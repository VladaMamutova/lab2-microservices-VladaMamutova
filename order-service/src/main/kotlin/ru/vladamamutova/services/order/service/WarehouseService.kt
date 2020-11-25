package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.model.Size
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

interface WarehouseService {
    fun takeItem(orderUid: UUID, model: String, size: Size
    ): Optional<OrderItemResponse>

    fun useWarranty(itemUid: UUID, request: OrderWarrantyRequest
    ): Optional<OrderWarrantyResponse>

    fun returnItem(itemUid: UUID)
}
