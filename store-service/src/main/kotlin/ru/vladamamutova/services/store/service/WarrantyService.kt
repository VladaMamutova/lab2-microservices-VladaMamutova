package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.warranty.model.WarrantyInfoResponse
import java.util.*

interface WarrantyService {
    fun getWarrantyInfo(itemUid: UUID): Optional<WarrantyInfoResponse>
}
