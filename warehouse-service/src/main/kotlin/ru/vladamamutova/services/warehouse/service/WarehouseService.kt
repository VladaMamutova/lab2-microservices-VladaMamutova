package ru.vladamamutova.services.warehouse.service

import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import ru.vladamamutova.services.warehouse.model.OrderItemRequest
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import java.util.*

interface WarehouseService {
    fun getItemInfo(orderItemUid: UUID): ItemInfoResponse
    fun takeItem(request: OrderItemRequest): OrderItemResponse
    fun returnItem(orderItemUid: UUID)
}
