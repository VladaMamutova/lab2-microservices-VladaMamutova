package ru.vladamamutova.services.store.exception

import java.lang.RuntimeException
import java.util.*

class OrderProcessException(message: String) : RuntimeException(message) {
    constructor(userUid: UUID) : this("Can't make order for user $userUid")
}
