package ru.vladamamutova.services.store.exception

import java.lang.RuntimeException
import java.util.*

class OrderNotFoundException(orderUid: UUID, userUid: UUID) :
        RuntimeException("Order $orderUid not found for user $userUid.")
