package ru.vladamamutova.services.order.model

import java.util.*

class OrderResponse (
        val itemUid: UUID,
        val orderDate: String,
        val orderUid: UUID,
        val status: PaymentStatus
)
