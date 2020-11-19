package ru.vladamamutova.services.warehouse.model

class ErrorValidationResponse(override val message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
