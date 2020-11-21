package ru.vladamamutova.services.warranty.exception

import java.util.*

class WarrantyNotFoundException(itemUid: UUID) :
        RuntimeException("Warranty not found for item $itemUid")
