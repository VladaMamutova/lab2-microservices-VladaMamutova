package ru.vladamamutova.services.order.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.order.controller.RestTemplateErrorHandler
import ru.vladamamutova.services.order.exception.WarehouseProcessException
import ru.vladamamutova.services.order.model.Size
import ru.vladamamutova.services.warehouse.model.OrderItemRequest
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

@Service
class WarehouseServiceImpl(
        @Value("\${warehouse.service.url}") private val warehouseServiceUrl: String
) : WarehouseService {
    override fun takeItem(orderUid: UUID, model: String, size: Size
    ): Optional<OrderItemResponse> {
        val orderItemRequest = OrderItemRequest(orderUid, model, size.name)

        val url = "$warehouseServiceUrl/api/v1/warehouse"

        val restTemplate = RestTemplate()

        // create headers to set 'content-type' and 'accept'
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

        // build the request entity with request body and headers
        val requestEntity: HttpEntity<OrderItemRequest> =
                HttpEntity(orderItemRequest, headers)

        // set custom error handler
        restTemplate.errorHandler = RestTemplateErrorHandler()

        // send POST request
        val responseEntity: ResponseEntity<OrderItemResponse> =
                restTemplate.postForEntity(
                        url,
                        requestEntity,
                        OrderItemResponse::class.java
                )

        return Optional.ofNullable(responseEntity.body)
    }

    override fun useWarranty(itemUid: UUID, request: OrderWarrantyRequest
    ): Optional<OrderWarrantyResponse> {
        val url = "$warehouseServiceUrl/api/v1/warehouse/{itemUid}/warranty"

        val restTemplate = RestTemplate()

        // create headers to set 'content-type' and 'accept'
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

        // set uri parameters
        val uriVariables: MutableMap<String, String> = HashMap()
        uriVariables["itemUid"] = itemUid.toString()

        // build the request entity with request body and headers
        val requestEntity: HttpEntity<OrderWarrantyRequest> =
                HttpEntity(request, headers)

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

        return Optional.ofNullable(responseEntity.body)
    }

    override fun returnItem(itemUid: UUID) {
        val url = "$warehouseServiceUrl/api/v1/warehouse/{itemUid}"

        try {
            val restTemplate = RestTemplate()
            restTemplate.delete(url, itemUid)
        } catch (ex: HttpStatusCodeException) {
            throw WarehouseProcessException(ex.responseBodyAsString)
        }
    }
}
