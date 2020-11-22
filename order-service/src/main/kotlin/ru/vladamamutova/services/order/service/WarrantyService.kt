package ru.vladamamutova.services.order.service

import java.util.*

interface WarrantyService {
    fun startWarranty(itemUid: UUID)
    fun closeWarranty(itemUid: UUID)
}
