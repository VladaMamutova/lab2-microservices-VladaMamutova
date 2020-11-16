package ru.vladamamutova.services.warehouse.model

import java.util.*

class OrderItemResponse(val orderItemUid: UUID, val orderUid: UUID,
                        val model: String, val size: Size)
