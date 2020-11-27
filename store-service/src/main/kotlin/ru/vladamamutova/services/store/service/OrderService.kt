package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderResponse
import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.WarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

interface OrderService {
    fun getUserOrder(userUid: UUID, orderUid: UUID): Optional<OrderResponse>
    fun getUserOrders(userUid: UUID): Optional<List<OrderResponse>>
    fun makeOrder(userUid: UUID, request: PurchaseRequest): Optional<CreateOrderResponse>
    fun returnOrder(orderUid: UUID)
    fun useWarranty(orderUid: UUID, request: WarrantyRequest): Optional<OrderWarrantyResponse>
}
