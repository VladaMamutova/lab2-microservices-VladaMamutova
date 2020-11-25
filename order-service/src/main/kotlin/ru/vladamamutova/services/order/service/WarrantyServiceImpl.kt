package ru.vladamamutova.services.order.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import ru.vladamamutova.services.order.exception.WarrantyProcessException
import ru.vladamamutova.services.order.model.ErrorResponse
import java.util.*

@Service
class WarrantyServiceImpl(
        @Value("\${warranty.service.url}") private val warrantyServiceUrl: String
) : WarrantyService {
    override fun startWarranty(itemUid: UUID) {
        val url = "$warrantyServiceUrl/api/v1/warranty/{itemUid}"

        try {
            val restTemplate = RestTemplate()
            restTemplate.postForEntity(url, null, Void::class.java, itemUid)
        } catch (ex: HttpStatusCodeException) {
            throw WarrantyProcessException(ex.extractMessage())
        }
    }

    override fun closeWarranty(itemUid: UUID) {
        val url = "$warrantyServiceUrl/api/v1/warranty/{itemUid}"

        try {
            val restTemplate = RestTemplate()
            restTemplate.delete(url, itemUid)
        } catch (ex: HttpStatusCodeException) {
            throw WarrantyProcessException(ex.extractMessage())
        }
    }

    fun HttpStatusCodeException.extractMessage(): String {
        return Gson().fromJson(this.responseBodyAsString, ErrorResponse::class.java).message
    }
}
