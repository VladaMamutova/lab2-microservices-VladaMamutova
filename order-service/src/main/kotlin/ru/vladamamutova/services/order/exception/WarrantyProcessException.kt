package ru.vladamamutova.services.order.exception

import java.util.*

class WarrantyProcessException(itemUid: UUID, orderUid: UUID) :
        RuntimeException("Can't process warranty request " +
                                 "for item '$itemUid' in order '$orderUid'")
