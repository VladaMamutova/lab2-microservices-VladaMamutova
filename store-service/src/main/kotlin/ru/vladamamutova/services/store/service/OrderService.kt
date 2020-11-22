package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.order.model.OrderResponse
import java.util.*

interface OrderService {
    fun getOrders(userUid: UUID): Optional<List<OrderResponse>>
    fun getOrderById(userUid: UUID): Optional<OrderResponse>
}
