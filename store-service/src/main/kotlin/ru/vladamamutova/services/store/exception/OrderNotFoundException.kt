package ru.vladamamutova.services.store.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFoundException(orderUid: UUID, userUid: UUID) :
        RuntimeException("Order $orderUid not found for user $userUid.")
