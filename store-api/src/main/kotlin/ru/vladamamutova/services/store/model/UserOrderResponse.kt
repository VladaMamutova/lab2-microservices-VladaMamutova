package ru.vladamamutova.services.store.model

import java.util.*

class UserOrderResponse(
        var date: String,
        var orderUid: UUID,
        var model: String? = null,
        var size: Size? = null,
        var warrantyDate: String? = null,
        var warrantyStatus: WarrantyStatus? = null
)
