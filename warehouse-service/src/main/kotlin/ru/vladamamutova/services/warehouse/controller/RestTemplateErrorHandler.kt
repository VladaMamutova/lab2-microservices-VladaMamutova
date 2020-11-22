package ru.vladamamutova.services.warehouse.controller

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import ru.vladamamutova.services.warehouse.exception.WarrantyProcessException
import java.util.*
import javax.persistence.EntityNotFoundException

@Component
class RestTemplateErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        return (response.statusCode.series() == HttpStatus.Series.CLIENT_ERROR
                || response.statusCode.series() == HttpStatus.Series.SERVER_ERROR)
    }

    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode.series() === HttpStatus.Series.CLIENT_ERROR) {
            if (response.statusCode === HttpStatus.NOT_FOUND) {
                throw EntityNotFoundException(response.getResponseBodyAsString())
            }
        }

        throw WarrantyProcessException(response.getResponseBodyAsString())
    }

    fun ClientHttpResponse.getResponseBodyAsString(): String {
        val scanner = Scanner(this.body).useDelimiter("\\A")
        return if (scanner.hasNext()) scanner.next() else ""
    }
}
