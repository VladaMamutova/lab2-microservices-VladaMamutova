package ru.vladamamutova.services.order.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderResponse
import java.util.*

@Service
class OrderServiceImpl: OrderService {
    override fun getUserOrder(userUid: UUID, orderUid: UUID
    ): OrderResponse {
        TODO("Not yet implemented")
    }

    override fun getUserOrders(userUid: UUID): List<OrderResponse> {
        TODO("Not yet implemented")
    }

    override fun createOrder(userUid: UUID, request: CreateOrderRequest
    ): CreateOrderResponse {
        TODO("Not yet implemented")
    }

    override fun returnOrder(orderUid: UUID) {
        TODO("Not yet implemented")
    }
}
