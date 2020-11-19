package ru.vladamamutova.services.warehouse.service

import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

interface WarrantyService {
    fun requestForWarrantySolution(orderItemUid: UUID, request: OrderWarrantyRequest): OrderWarrantyResponse
}
