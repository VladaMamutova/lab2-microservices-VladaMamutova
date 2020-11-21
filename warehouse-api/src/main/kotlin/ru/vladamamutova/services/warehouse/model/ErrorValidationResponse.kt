package ru.vladamamutova.services.warehouse.model

class ErrorValidationResponse(message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
