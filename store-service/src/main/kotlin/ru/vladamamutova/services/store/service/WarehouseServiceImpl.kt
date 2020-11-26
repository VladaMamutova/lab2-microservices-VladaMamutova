package ru.vladamamutova.services.store.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.store.exception.OrderProcessException
import ru.vladamamutova.services.store.exception.WarehouseProcessException
import ru.vladamamutova.services.store.model.ErrorResponse
import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class WarehouseServiceImpl(
        @Value("\${warehouse.service.url}") private val warehouseServiceUrl: String
) : WarehouseService {
    override fun getItemInfo(itemUid: UUID): Optional<ItemInfoResponse> {
        val url = "$warehouseServiceUrl/api/v1/warehouse/{itemUid}"

        try {
            val restTemplate = RestTemplate()

            // create headers to set 'accept'
            val headers = HttpHeaders()
            headers.accept =
                    Collections.singletonList(MediaType.APPLICATION_JSON)

            // build the request entity with headers
            val requestEntity: HttpEntity<*> = HttpEntity<Any?>(headers)

            val responseEntity: ResponseEntity<ItemInfoResponse> =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            ItemInfoResponse::class.java,
                            itemUid
                    )
            return Optional.ofNullable(responseEntity.body)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                throw EntityNotFoundException(ex.extractMessage())
            }
            throw WarehouseProcessException(ex.extractMessage())
        }
    }

    fun HttpStatusCodeException.extractMessage(): String {
        return Gson().fromJson(
                this.responseBodyAsString,
                ErrorResponse::class.java
        ).message
    }
}
