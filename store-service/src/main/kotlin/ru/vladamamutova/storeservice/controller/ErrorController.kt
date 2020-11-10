package ru.vladamamutova.storeservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vladamamutova.storeservice.domain.ErrorResponse
import ru.vladamamutova.storeservice.domain.ErrorValidationResponse
import ru.vladamamutova.storeservice.exception.UserNotFoundException
import java.lang.Exception

@RestControllerAdvice
class ErrorController {
    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFoundException(exception: UserNotFoundException) : ErrorResponse {
        logger.error("User Not Found Exception: " + exception.message)
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
        return ErrorValidationResponse("Not Valid Arguments", errors)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllExceptions(exception: Exception) : ErrorResponse {
        logger.error("Unexpected exception", exception)
        return ErrorResponse(
                exception.message ?: exception.stackTrace.toString()
        )
    }
}