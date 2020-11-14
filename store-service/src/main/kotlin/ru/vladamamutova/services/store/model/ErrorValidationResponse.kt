package ru.vladamamutova.services.store.model

class ErrorValidationResponse(override val message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
