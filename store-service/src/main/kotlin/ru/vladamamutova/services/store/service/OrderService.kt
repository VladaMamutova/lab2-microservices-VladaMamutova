package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.order.model.OrderResponse
import java.util.*

interface OrderService {
    fun getUserOrder(userUid: UUID, orderUid: UUID): Optional<OrderResponse>
    fun getUserOrders(userUid: UUID): Optional<List<OrderResponse>>
}
