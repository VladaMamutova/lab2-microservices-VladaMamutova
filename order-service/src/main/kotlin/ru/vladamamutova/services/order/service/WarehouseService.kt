package ru.vladamamutova.services.order.service

import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import java.util.*

interface WarehouseService {
    fun useWarranty(orderUid: UUID, request: OrderWarrantyRequest
    ): OrderWarrantyResponse
}
