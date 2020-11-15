package ru.vladamamutova.services.warranty.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class WarrantyNotFoundException(itemUid: UUID) :
        RuntimeException("Warranty not found for item $itemUid.")
