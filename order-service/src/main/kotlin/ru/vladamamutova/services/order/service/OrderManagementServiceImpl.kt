package ru.vladamamutova.services.order.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vladamamutova.services.order.exception.WarehouseProcessException
import ru.vladamamutova.services.order.exception.WarrantyProcessException
import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*

@Service
class OrderManagementServiceImpl(private val orderService: OrderService,
                                 private val warehouseService: WarehouseService,
                                 private val warrantyService: WarrantyService
) : OrderManagementService {
    private val logger =
            LoggerFactory.getLogger(OrderManagementServiceImpl::class.java)

    override fun makeOrder(userUid: UUID, request: CreateOrderRequest
    ): CreateOrderResponse {
        val model = request.model
        val size = request.size
        logger.info("Make order (model: $model, size: $size) for user $userUid")

        val orderUid = UUID.randomUUID()
        logger.info("Request to WarehouseService to take item (model: $model, " +
                            "size: $size) for order $orderUid")

        val orderItemResponse = warehouseService
            .takeItem(orderUid, model, size)
            .orElseThrow {
                throw WarehouseProcessException(
                        "Can't take item (model: $model, size: $size) " +
                                "for user $userUid"
                )
            }

        val orderItemUid = orderItemResponse.orderItemUid

        logger.info(
                "Request to WarrantyService to start warranty " +
                        "on item $orderItemUid for user $userUid"
        )
        warrantyService.startWarranty(orderItemUid)
        orderService.createOrder(orderItemUid, orderUid, userUid)

        return CreateOrderResponse(orderUid)
    }

    override fun useWarranty(orderUid: UUID, request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        logger.info("Use warranty (reason: ${request.reason}) for order '$orderUid'")

        val order = orderService.getOrderByUid(orderUid)
        val orderItemUid = order.itemUid!!

        logger.info("Request to WarrantyService to use warranty for item " +
                            "$orderItemUid in order $orderUid")
        return warehouseService
            .useWarranty(orderItemUid, request)
            .orElseThrow {
                throw WarrantyProcessException(
                        orderItemUid,
                        orderUid
                )
            }
    }

    override fun returnOrder(orderUid: UUID) {
        logger.info("Return order $orderUid")

        val order = orderService.getOrderByUid(orderUid)
        val orderItemUid = order.itemUid!!

        warehouseService.returnItem(orderItemUid)
        logger.info("Request to WarehouseService to return item $orderItemUid " +
                            "for order $orderUid")

        warrantyService.closeWarranty(orderItemUid)
        logger.info("Request to WarrantyService to close warranty for item " +
                            "$orderItemUid in order $orderUid")

        orderService.returnOrder(orderUid)
    }
}
