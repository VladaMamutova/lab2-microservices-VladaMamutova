package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderResponse
import java.util.*

interface OrderService {
    fun getUserOrder(userUid: UUID, orderUid: UUID): OrderResponse
    fun getUserOrders(userUid: UUID): List<OrderResponse>
    fun createOrder(userUid: UUID, request: CreateOrderRequest
    ): CreateOrderResponse

    fun returnOrder(orderUid: UUID)
}
