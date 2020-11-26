package ru.vladamamutova.services.store.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.order.model.OrderResponse
import ru.vladamamutova.services.store.exception.OrderProcessException
import ru.vladamamutova.services.store.model.ErrorResponse
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

    fun HttpStatusCodeException.extractMessage(): String {
        return Gson().fromJson(
                this.responseBodyAsString,
                ErrorResponse::class.java
        ).message
    }
}
