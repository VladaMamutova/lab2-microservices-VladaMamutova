package ru.vladamamutova.services.warehouse.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.warehouse.controller.RestTemplateErrorHandler
import ru.vladamamutova.services.warehouse.exception.WarrantyProcessException
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.ItemWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

@Service
class WarrantyServiceImpl(
        // С помощью аннотации @Value внедряем значение warranty.service.url
        // из файла свойств, выбор которого будет зависеть от активного профиля
        // (напр., application-local.properties)
        @Value("\${warranty.service.url}")
        private val warrantyServiceUrl: String,
        private val warehouseService: WarehouseService
) : WarrantyService {
    private val logger =
            LoggerFactory.getLogger(WarrantyServiceImpl::class.java)

    override fun requestForWarrantySolution(orderItemUid: UUID,
                                            request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        logger.info("Request for warranty solution (reason: ${request.reason}) " +
                            "on item $orderItemUid")

        val availableCount = warehouseService.getItemAvailableCount(orderItemUid)
        val warrantyRequest = ItemWarrantyRequest(request.reason, availableCount)
        logger.info("Request to WarrantyService to check warranty " +
                            "on item $orderItemUid")

        val url = "$warrantyServiceUrl/api/v1/warranty/{orderItemUid}/warranty"

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

        // set custom error handler
        restTemplate.errorHandler = RestTemplateErrorHandler()

        // send POST request
        val responseEntity: ResponseEntity<OrderWarrantyResponse> =
                restTemplate.postForEntity(
                        url,
                        requestEntity,
                        OrderWarrantyResponse::class.java,
                        uriVariables
                )

        // check response
        if (responseEntity.statusCode != HttpStatus.OK) {
            throw WarrantyProcessException(orderItemUid)
        } else {
            return responseEntity.body!!
        }
    }
}
