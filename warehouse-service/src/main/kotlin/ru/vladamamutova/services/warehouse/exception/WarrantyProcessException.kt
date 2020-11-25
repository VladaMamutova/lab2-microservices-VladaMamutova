package ru.vladamamutova.services.warehouse.exception

import java.util.*

class WarrantyProcessException(message: String) : RuntimeException(message) {

    constructor(itemUid: UUID
    ) : this("Can't process warranty request for item '$itemUid'")
}
