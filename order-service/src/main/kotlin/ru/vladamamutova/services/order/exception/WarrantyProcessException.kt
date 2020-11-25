package ru.vladamamutova.services.order.exception

import java.util.*

class WarrantyProcessException(message: String) : RuntimeException(message) {

    constructor(itemUid: UUID, orderUid: UUID) :
    this("Can't process warranty request for item '$itemUid' in order '$orderUid'")
}
