package ru.vladamamutova.services.order.exception

import java.lang.RuntimeException
import java.util.*

class OrderNotFoundException : RuntimeException {
    private constructor(message: String) : super(message)

    constructor(orderUid: UUID, userUid: UUID
    ) : this("Order $orderUid not found for user $userUid")

    constructor(orderUid: UUID) : this("Order $orderUid not found")
}
