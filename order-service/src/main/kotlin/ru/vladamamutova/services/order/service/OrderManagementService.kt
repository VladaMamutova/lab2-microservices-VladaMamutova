package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import java.util.*

interface OrderManagementService {
    fun makeOrder(userUid: UUID, request: CreateOrderRequest
    ): CreateOrderResponse

    fun useWarranty(orderUid: UUID, request: OrderWarrantyRequest
    ): OrderWarrantyResponse

    fun returnOrder(orderUid: UUID)
}
