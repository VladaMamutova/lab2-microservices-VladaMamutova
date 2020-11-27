package ru.vladamamutova.services.store.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderResponse
import ru.vladamamutova.services.order.model.Size
import ru.vladamamutova.services.store.exception.ItemNotAvailableException
import ru.vladamamutova.services.store.exception.OrderProcessException
import ru.vladamamutova.services.store.exception.WarrantyProcessException
import ru.vladamamutova.services.store.model.ErrorResponse
import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.WarrantyRequest
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*
import javax.persistence.EntityNotFoundException


@Service
class OrderServiceImpl(
        @Value("\${order.service.url}") private val orderServiceUrl: String
) : OrderService {
    override fun getUserOrder(userUid: UUID, orderUid: UUID
    ): Optional<OrderResponse> {
        val url = "$orderServiceUrl/api/v1/orders/{userUid}/{orderUid}"

        val restTemplate = RestTemplate()

        // create headers to set 'accept'
        val headers = HttpHeaders()
        headers.accept =
                Collections.singletonList(MediaType.APPLICATION_JSON)

        // build the request entity with headers
        val requestEntity: HttpEntity<*> = HttpEntity<Any?>(headers)

        try {
            val responseEntity: ResponseEntity<OrderResponse> =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            OrderResponse::class.java,
                            userUid, orderUid
                    )
            return Optional.ofNullable(responseEntity.body)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                throw EntityNotFoundException(ex.extractMessage())
            }
            throw OrderProcessException(ex.extractMessage())
        }
    }

    override fun getUserOrders(userUid: UUID): Optional<List<OrderResponse>> {
        val url = "$orderServiceUrl/api/v1/orders/{userUid}"

        val restTemplate = RestTemplate()

        // create headers to set 'accept'
        val headers = HttpHeaders()
        headers.accept =
                Collections.singletonList(MediaType.APPLICATION_JSON)

        // build the request entity with headers
        val requestEntity: HttpEntity<*> = HttpEntity<Any?>(headers)

        try {
            val responseEntity: ResponseEntity<Array<OrderResponse>> =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            Array<OrderResponse>::class.java,
                            userUid
                    )
            return Optional.ofNullable(responseEntity.body?.toList())
        } catch (ex: HttpStatusCodeException) {
            throw OrderProcessException(ex.extractMessage())
        }
    }

    override fun makeOrder(userUid: UUID, request: PurchaseRequest
    ): Optional<CreateOrderResponse> {
        val createOrderRequest = CreateOrderRequest(
                request.model,
                Size.valueOf(request.size.name)
        )

        val url = "$orderServiceUrl/api/v1/orders/{userUid}"

        val restTemplate = RestTemplate()

        // create headers to set 'content-type' and 'accept'
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

        // build the request entity with request body and headers
        val requestEntity: HttpEntity<CreateOrderRequest> =
                HttpEntity(createOrderRequest, headers)

        try {
            // send POST request
            val responseEntity: ResponseEntity<CreateOrderResponse> =
                    restTemplate.postForEntity(
                            url,
                            requestEntity,
                            CreateOrderResponse::class.java,
                            userUid
                    )

            return Optional.ofNullable(responseEntity.body)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.CONFLICT) {
                throw ItemNotAvailableException(ex.extractMessage())
            } else if (ex.statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw OrderProcessException(ex.extractMessage())
            }
            throw OrderProcessException(ex.extractMessage())
        }
    }

    override fun returnOrder(orderUid: UUID) {
        val url = "$orderServiceUrl/api/v1/orders/{orderUid}"

        try {
            val restTemplate = RestTemplate()
            restTemplate.delete(url, orderUid)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND){
                throw EntityNotFoundException(ex.extractMessage())
            } else if (ex.statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw OrderProcessException(ex.extractMessage())
            }
            throw OrderProcessException(ex.extractMessage())
        }
    }

    override fun useWarranty(orderUid: UUID, request: WarrantyRequest
    ): Optional<OrderWarrantyResponse> {
        val orderWarrantyRequest = OrderWarrantyRequest(request.reason)

        val url = "$orderServiceUrl/api/v1/orders/{orderUid}/warranty"

        val restTemplate = RestTemplate()

        // create headers to set 'content-type' and 'accept'
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

        // build the request entity with request body and headers
        val requestEntity: HttpEntity<OrderWarrantyRequest> =
                HttpEntity(orderWarrantyRequest, headers)

        try {
            // send POST request
            val responseEntity: ResponseEntity<OrderWarrantyResponse> =
                    restTemplate.postForEntity(
                            url,
                            requestEntity,
                            OrderWarrantyResponse::class.java,
                            orderUid
                    )

            return Optional.ofNullable(responseEntity.body)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                throw EntityNotFoundException(ex.extractMessage())
            } else if (ex.statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw WarrantyProcessException(ex.extractMessage())
            }
            throw OrderProcessException(ex.extractMessage())
        }
    }

    fun HttpStatusCodeException.extractMessage(): String {
        return Gson().fromJson(
                this.responseBodyAsString,
                ErrorResponse::class.java
        ).message
    }
}
