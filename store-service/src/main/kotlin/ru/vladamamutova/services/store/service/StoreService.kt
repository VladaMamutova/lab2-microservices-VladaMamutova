package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.UserOrderResponse
import ru.vladamamutova.services.store.model.WarrantyRequest
import ru.vladamamutova.services.store.model.WarrantyResponse
import java.util.*

interface StoreService {
    fun getUserOrders(userUid: UUID): List<UserOrderResponse>
    fun getUserOrderById(userUid: UUID, orderUid: UUID): UserOrderResponse
    fun requestWarranty(userUid: UUID, orderUid: UUID, request: WarrantyRequest): WarrantyResponse
    fun makePurchase(userUid: UUID, request: PurchaseRequest): UUID
    fun refundPurchase(userUid: UUID, orderUid: UUID)
}
