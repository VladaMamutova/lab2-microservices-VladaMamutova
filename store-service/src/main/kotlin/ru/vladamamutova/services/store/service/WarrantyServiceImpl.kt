package ru.vladamamutova.services.store.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.store.exception.WarrantyProcessException
import ru.vladamamutova.services.store.model.ErrorResponse
import ru.vladamamutova.services.warranty.model.WarrantyInfoResponse
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class WarrantyServiceImpl(
        @Value("\${warranty.service.url}") private val warrantyServiceUrl: String
) : WarrantyService {
    override fun getWarrantyInfo(itemUid: UUID
    ): Optional<WarrantyInfoResponse> {
        val url = "$warrantyServiceUrl/api/v1/warranty/{itemUid}"

        try {
            val restTemplate = RestTemplate()

            // create headers to set 'accept'
            val headers = HttpHeaders()
            headers.accept =
                    Collections.singletonList(MediaType.APPLICATION_JSON)

            // build the request entity with headers
            val requestEntity: HttpEntity<*> = HttpEntity<Any?>(headers)

            val responseEntity: ResponseEntity<WarrantyInfoResponse> =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            WarrantyInfoResponse::class.java,
                            itemUid
                    )
            return Optional.ofNullable(responseEntity.body)
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                throw EntityNotFoundException(ex.extractMessage())
            }
            throw WarrantyProcessException(ex.extractMessage())
        }
    }

    fun HttpStatusCodeException.extractMessage(): String {
        return Gson().fromJson(
                this.responseBodyAsString,
                ErrorResponse::class.java
        ).message
    }
}
