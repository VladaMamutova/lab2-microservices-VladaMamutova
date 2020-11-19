package ru.vladamamutova.services.warehouse.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vladamamutova.services.warehouse.exception.ItemNotAvailableException
import ru.vladamamutova.services.warehouse.exception.ItemNotFoundException
import ru.vladamamutova.services.warehouse.model.ErrorResponse
import ru.vladamamutova.services.warehouse.model.ErrorValidationResponse
import java.lang.Exception

@RestControllerAdvice
class ErrorController {
    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFoundException(exception: ItemNotFoundException) : ErrorResponse {
        logger.error("Item Not Found Exception: " + exception.message)
        return ErrorResponse(
                exception.message ?: exception.stackTrace.toString()
        )
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ItemNotAvailableException::class)
    fun handleItemNotAvailableException(exception: ItemNotAvailableException) : ErrorResponse {
        logger.error("Item Not Available Exception: " + exception.message)
        return ErrorResponse(
                exception.message ?: exception.stackTrace.toString()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException)
            : ErrorValidationResponse {
        val errors: Map<String, String> =
                exception.bindingResult.fieldErrors.associateBy(
                        { it.field },
                        { "${it.rejectedValue} is wrong value: ${it.defaultMessage}" })
        logger.error("Validation Exception: $errors")
        return ErrorValidationResponse(
                "Not Valid Arguments",
                errors
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllExceptions(exception: Exception) : ErrorResponse {
        logger.error("Unexpected exception", exception)
        return ErrorResponse(
                exception.message ?: exception.stackTrace.toString()
        )
    }
}
