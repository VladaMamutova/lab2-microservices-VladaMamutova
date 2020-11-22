package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import java.util.*

interface WarehouseService {
    fun takeItem(orderUid: UUID, model: String, size: String
    ): Optional<OrderItemResponse>

    fun useWarranty(itemUid: UUID, request: OrderWarrantyRequest
    ): Optional<OrderWarrantyResponse>

    fun returnItem(itemUid: UUID)
}
