package ru.vladamamutova.storeservice.service

import ru.vladamamutova.storeservice.model.OrderResponse
import java.util.*

interface OrderService {
    fun getOrders(userUid: UUID): Optional<List<OrderResponse>>
    fun getOrderById(userUid: UUID): Optional<OrderResponse>
}
