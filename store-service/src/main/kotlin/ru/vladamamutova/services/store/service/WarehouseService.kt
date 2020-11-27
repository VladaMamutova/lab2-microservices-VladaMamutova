package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import java.util.*

interface WarehouseService {
    fun getItemInfo(itemUid: UUID): Optional<ItemInfoResponse>
}
