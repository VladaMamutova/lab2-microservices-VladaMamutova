package ru.vladamamutova.services.warehouse.service

import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.ItemWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

@Service
class WarrantyServiceImpl(private val warehouseService: WarehouseService) :
        WarrantyService {
    private val logger =
            LoggerFactory.getLogger(WarehouseServiceImpl::class.java)

    override fun requestForWarrantySolution(orderItemUid: UUID,
                                            request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        val availableCount =
                warehouseService.getItemAvailableCount(orderItemUid)
        val warrantyRequest =
                ItemWarrantyRequest(request.reason, availableCount)
        logger.info(
                "Warranty request (reason: {}) on item '{}'",
                request.reason,
                orderItemUid
        )
        logger.info(
                "Request to WarrantyService to check warranty on item '{}'",
                orderItemUid
        )

        val url =
                "http://warranty-service:8180/api/v1/warranty/{orderItemUid}/warranty"

        val restTemplate = RestTemplate()

        // create headers to set 'content-type' and 'accept'
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

        // set uri parameters
        val uriVariables: MutableMap<String, String> = HashMap()
        uriVariables["orderItemUid"] = orderItemUid.toString()

        // build the request entity with request body and headers
        val requestEntity: HttpEntity<ItemWarrantyRequest> =
                HttpEntity(warrantyRequest, headers)

        // send POST request
        val responseEntity: ResponseEntity<OrderWarrantyResponse> =
                restTemplate.postForEntity(
                        url,
                        requestEntity,
                        OrderWarrantyResponse::class.java,
                        uriVariables
                )

        // check response
        if (responseEntity.statusCode == HttpStatus.OK) {
            return responseEntity.body!!
        } else {
            throw RuntimeException("statusCode: $responseEntity.statusCode")
        }
    }
}
