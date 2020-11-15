package ru.vladamamutova.services.warranty.model

class ErrorValidationResponse(override val message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
