package ru.vladamamutova.services.order.controller

import com.google.gson.Gson
import ru.vladamamutova.services.order.exception.ItemNotAvailableException
import ru.vladamamutova.services.order.exception.WarehouseProcessException
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import ru.vladamamutova.services.order.model.ErrorResponse
import java.util.*
import javax.persistence.EntityNotFoundException

@Component
class RestTemplateErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        return (response.statusCode.series() == HttpStatus.Series.CLIENT_ERROR
                || response.statusCode.series() == HttpStatus.Series.SERVER_ERROR)
    }

    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode.series() == HttpStatus.Series.CLIENT_ERROR) {
            when (response.statusCode) {
                HttpStatus.NOT_FOUND -> {
                    throw EntityNotFoundException(response.extractMessage())
                }
                HttpStatus.CONFLICT -> {
                    throw ItemNotAvailableException(response.extractMessage())
                }
                HttpStatus.UNPROCESSABLE_ENTITY -> {
                    throw WarehouseProcessException(response.extractMessage())
                }
                else -> throw WarehouseProcessException(response.extractMessage())
            }
        }

        throw WarehouseProcessException(response.extractMessage())
    }

    fun ClientHttpResponse.extractMessage(): String {
        val scanner = Scanner(this.body).useDelimiter("\\A")
        return if (scanner.hasNext()) {
            Gson().fromJson(scanner.next(), ErrorResponse::class.java).message
        } else ""
    }
}
