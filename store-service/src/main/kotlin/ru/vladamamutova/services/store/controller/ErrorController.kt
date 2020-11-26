package ru.vladamamutova.services.store.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vladamamutova.services.store.exception.UserNotFoundException
import ru.vladamamutova.services.store.exception.OrderNotFoundException
import ru.vladamamutova.services.store.exception.WarehouseProcessException
import ru.vladamamutova.services.store.exception.WarrantyProcessException
import ru.vladamamutova.services.store.model.ErrorResponse
import ru.vladamamutova.services.store.model.ErrorValidationResponse
import java.lang.Exception
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class ErrorController {
    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [UserNotFoundException::class, OrderNotFoundException::class, EntityNotFoundException::class])
    fun handleNotFoundException(exception: RuntimeException) : ErrorResponse {
        logger.error("Not Found Exception: " + exception.message)
        return ErrorResponse(
                exception.message ?: exception.stackTrace.toString()
        )
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = [WarehouseProcessException::class, WarrantyProcessException::class])
    fun handleProcessExceptions(exception: RuntimeException): ErrorResponse {
        logger.error(exception::class.java.simpleName + ": " + exception.message)
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