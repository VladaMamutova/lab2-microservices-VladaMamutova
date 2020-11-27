package ru.vladamamutova.services.store.model

import java.util.*

class WarrantyResponse(val orderUid: UUID, val decision: String,
                       val warrantyDate: String
)
