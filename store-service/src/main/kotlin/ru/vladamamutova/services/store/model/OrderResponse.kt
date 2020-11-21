package ru.vladamamutova.services.store.model


import ru.vladamamutova.services.order.model.PaymentStatus
import java.util.*

class OrderResponse (
        var itemUid: UUID,
        var orderDate: String,
        var orderUid: UUID,
        var status: PaymentStatus
)
