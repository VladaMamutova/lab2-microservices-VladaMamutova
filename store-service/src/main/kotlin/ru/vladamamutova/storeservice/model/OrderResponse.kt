package ru.vladamamutova.storeservice.model

import java.util.*

class OrderResponse (
        var itemUid: UUID,
        var orderDate: String,
        var orderUid: UUID,
        var status: PaymentStatus
)
