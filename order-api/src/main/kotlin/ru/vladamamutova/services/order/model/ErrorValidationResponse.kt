package ru.vladamamutova.services.order.model

class ErrorValidationResponse(message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
