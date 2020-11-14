package ru.vladamamutova.services.warranty.service

import ru.vladamamutova.services.warranty.domain.Warranty
import ru.vladamamutova.services.warranty.model.ItemWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

interface WarrantyService {
    fun getWarrantyByItemUid(itemUid: UUID): Warranty
    fun checkWarranty(itemUid: UUID, request: ItemWarrantyRequest
    ): OrderWarrantyResponse

    fun startWarranty(itemUid: UUID)
    fun closeWarranty(itemUid: UUID)
}