package ru.vladamamutova.services.store.service


import ru.vladamamutova.services.store.exception.OrderNotFoundException
import ru.vladamamutova.services.store.exception.UserNotFoundException
import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.UserOrderResponse
import ru.vladamamutova.services.store.model.WarrantyResponse
import java.util.*

class StoreServiceImpl(private val userService: UserService,
                       private val orderService: OrderService
) : StoreService {
    override fun getUserOrders(userUid: UUID): List<UserOrderResponse> {
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        val userOrders = mutableListOf<UserOrderResponse>()
        val orders = orderService.getOrders(userUid)
        if (orders.isPresent) {
            for (order in orders.get()) {
                userOrders.add(
                        UserOrderResponse(
                                date = order.orderDate,
                                orderUid = order.orderUid
                        )
                )
            }
        }
        return userOrders
    }

    override fun getUserOrderById(userUid: UUID, orderUid: UUID
    ): UserOrderResponse {
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        val order = orderService
            .getOrderById(userUid)
            .orElseThrow { throw OrderNotFoundException(orderUid, userUid) }

        return UserOrderResponse(
                date = order.orderDate,
                orderUid = order.orderUid
        )
    }

    override fun getOrderWarranty(userUid: UUID, orderUid: UUID
    ): WarrantyResponse {
        TODO("Not yet implemented")
    }

    override fun makePurchase(userUid: UUID, purchaseRequest: PurchaseRequest
    ): UUID {
        TODO("Not yet implemented")
    }

    override fun giveRefund(userUid: UUID, orderUid: UUID) {
        TODO("Not yet implemented")
    }
}
