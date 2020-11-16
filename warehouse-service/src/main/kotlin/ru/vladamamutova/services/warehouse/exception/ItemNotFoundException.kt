package ru.vladamamutova.services.warehouse.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ru.vladamamutova.services.warehouse.model.Size
import java.lang.RuntimeException
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemNotFoundException(message: String) :
        RuntimeException(message) {

    constructor(model: String, size: Size
    ) : this("Item with model $model and size $size not found")

    constructor(orderItemUid: UUID
    ) : this("Item not found for orderItem $orderItemUid")
}
