package ru.vladamamutova.services.store.model

import java.util.*

class UserOrderResponse(
        var orderUid: UUID,
        var date: String,
        var model: String? = null,
        var size: Size? = null,
        var warrantyDate: String? = null,
        var warrantyStatus: WarrantyStatus? = null
)
