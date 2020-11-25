package ru.vladamamutova.services.store.model

class ErrorValidationResponse(message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
