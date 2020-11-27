package ru.vladamamutova.services.store.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vladamamutova.services.store.exception.OrderNotFoundException
import ru.vladamamutova.services.store.exception.OrderProcessException
import ru.vladamamutova.services.store.exception.UserNotFoundException
import ru.vladamamutova.services.store.exception.WarrantyProcessException
import ru.vladamamutova.services.store.model.*
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

@Service
class StoreServiceImpl(
        private val userService: UserService,
        private val orderService: OrderService,
        private val warehouseService: WarehouseService,
        private val warrantyService: WarrantyService
) : StoreService {
    private val logger = LoggerFactory.getLogger(StoreServiceImpl::class.java)

    override fun getUserOrders(userUid: UUID): List<UserOrderResponse> {
        logger.info("Get user $userUid orders")
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        val userOrders = mutableListOf<UserOrderResponse>()

        logger.info("Request to Order Service to get user $userUid orders")
        val orders = orderService.getUserOrders(userUid)
        if (orders.isPresent) {
            logger.info("User $userUid has ${orders.get().size} orders")
            for ((index, order) in orders.get().withIndex()) {
                logger.info("Processing ${index + 1} order ${order.orderUid}")

                val userOrder =
                        UserOrderResponse(order.orderUid, order.orderDate)
                val itemUid = order.itemUid

                logger.info("Request to Warehouse Service to get item $itemUid")
                val itemInfo = warehouseService.getItemInfo(itemUid)
                if (itemInfo.isPresent) {
                    userOrder.model = itemInfo.get().model
                    userOrder.size = Size.valueOf(itemInfo.get().size)
                }

                logger.info("Request to Warranty Service to get warranty info for item $itemUid")
                val warrantyInfo = warrantyService.getWarrantyInfo(itemUid)
                if (warrantyInfo.isPresent) {
                    userOrder.warrantyDate = warrantyInfo.get().warrantyDate
                    userOrder.warrantyStatus =
                            WarrantyStatus.valueOf(warrantyInfo.get().status)
                }

                userOrders.add(userOrder)
            }
        } else {
            logger.info("User $userUid has no orders")
        }
        return userOrders
    }

    override fun getUserOrderById(userUid: UUID, orderUid: UUID
    ): UserOrderResponse {
        logger.info("Get user $userUid order $orderUid")
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        logger.info("Request to Order Service to get user $userUid order $orderUid")
        val order = orderService
            .getUserOrder(userUid, orderUid)
            .orElseThrow { throw OrderNotFoundException(orderUid, userUid) }

        val userOrder = UserOrderResponse(order.orderUid, order.orderDate)
        val itemUid = order.itemUid

        logger.info("Request to Warehouse Service to get item $itemUid")
        val itemInfo = warehouseService.getItemInfo(itemUid)
        if (itemInfo.isPresent) {
            userOrder.model = itemInfo.get().model
            userOrder.size = Size.valueOf(itemInfo.get().size)
        }

        logger.info("Request to Warranty Service to get warranty info for item $itemUid")
        val warrantyInfo = warrantyService.getWarrantyInfo(itemUid)
        if (warrantyInfo.isPresent) {
            userOrder.warrantyDate = warrantyInfo.get().warrantyDate
            userOrder.warrantyStatus =
                    WarrantyStatus.valueOf(warrantyInfo.get().status)
        }

        return userOrder
    }

    override fun requestWarranty(userUid: UUID, orderUid: UUID,
                                 request: WarrantyRequest
    ): WarrantyResponse {
        logger.info(
                "Request warranty for order $orderUid (reason: ${request.reason}) " +
                        "by user $userUid"
        )
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        logger.info("Request to Order Service to use warranty for order $orderUid")
        val response: OrderWarrantyResponse = orderService
            .useWarranty(orderUid, request)
            .orElseThrow { throw WarrantyProcessException(orderUid) }

        return WarrantyResponse(
                orderUid,
                response.decision,
                response.warrantyDate
        )
    }

    override fun makePurchase(userUid: UUID, request: PurchaseRequest
    ): UUID {
        logger.info("Make purchase (model: ${request.model}, " +
                            "size: ${request.size}) for user $userUid")
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        logger.info("Request to Order Service to make order for user $userUid")
        return orderService
            .makeOrder(userUid, request)
            .orElseThrow { throw OrderProcessException(userUid) }.orderUid
    }

    override fun refundPurchase(userUid: UUID, orderUid: UUID) {
        logger.info("Return user $userUid order $orderUid")
        if (!userService.existById(userUid)) {
            throw UserNotFoundException(userUid)
        }

        logger.info("Request to Order Service to return order $orderUid")
        orderService.returnOrder(orderUid)
    }
}
