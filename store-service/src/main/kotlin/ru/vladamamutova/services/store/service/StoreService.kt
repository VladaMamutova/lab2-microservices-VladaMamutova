package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.UserOrderResponse
import ru.vladamamutova.services.store.model.WarrantyResponse
import java.util.*

interface StoreService {
    fun getUserOrders(userUid: UUID): List<UserOrderResponse>
    fun getUserOrderById(userUid: UUID, orderUid: UUID): UserOrderResponse
    fun getOrderWarranty(userUid: UUID, orderUid: UUID): WarrantyResponse
    fun makePurchase(userUid: UUID, purchaseRequest: PurchaseRequest): UUID
    fun giveRefund(userUid: UUID, orderUid: UUID)
}
