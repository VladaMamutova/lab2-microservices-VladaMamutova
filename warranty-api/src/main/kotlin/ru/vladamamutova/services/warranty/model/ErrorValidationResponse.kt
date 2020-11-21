package ru.vladamamutova.services.warranty.model

class ErrorValidationResponse(message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
