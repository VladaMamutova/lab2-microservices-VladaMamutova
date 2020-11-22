package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.domain.Order
import ru.vladamamutova.services.order.model.OrderResponse
import java.util.*

interface OrderService {
    fun getUserOrder(userUid: UUID, orderUid: UUID): OrderResponse
    fun getUserOrders(userUid: UUID): List<OrderResponse>
    fun createOrder(itemUid: UUID, orderUid: UUID, userUid: UUID)
    fun returnOrder(orderUid: UUID)
    fun getOrderByUid(orderUid: UUID): Order
}
