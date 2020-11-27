package ru.vladamamutova.services.store.exception

import java.lang.RuntimeException
import java.util.*

class WarrantyProcessException(message: String) : RuntimeException(message) {

    constructor(orderUid: UUID
    ) : this("Can't process warranty request for order $orderUid'")
}
