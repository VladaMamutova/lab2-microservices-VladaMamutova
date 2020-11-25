package ru.vladamamutova.services.warehouse.exception

import java.lang.RuntimeException
import java.util.*

class ItemNotFoundException : RuntimeException {
    private constructor(message: String) : super(message)

    constructor(model: String, size: String
    ) : this("Item with model $model and size $size not found")

    constructor(orderItemUid: UUID
    ) : this("Item not found for orderItem $orderItemUid")
}
