package ru.vladamamutova.services.order.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.order.exception.WarehouseProcessException
import ru.vladamamutova.services.order.exception.WarrantyProcessException
import ru.vladamamutova.services.order.model.CreateOrderRequest
import ru.vladamamutova.services.order.model.CreateOrderResponse
import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import java.util.*

@Service
class OrderManagementServiceImpl(private val orderService: OrderService,
                                 private val warehouseService: WarehouseService,
                                 private val warrantyService: WarrantyService
) : OrderManagementService {
    override fun makeOrder(userUid: UUID, request: CreateOrderRequest
    ): CreateOrderResponse {
        val model = request.model
        val size = request.size

        val orderUid = UUID.randomUUID()
        val orderItemResponse = warehouseService
            .takeItem(orderUid, model, size)
            .orElseThrow { throw WarehouseProcessException("Can't take item (model: $model, size: $size) for user $userUid") }

        val orderItemUid = orderItemResponse.orderItemUid

        warrantyService.startWarranty(orderItemUid)
        orderService.createOrder(orderItemUid, orderUid, userUid)

        return CreateOrderResponse(orderUid)
    }

    override fun useWarranty(orderUid: UUID, request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        val order = orderService.getOrderByUid(orderUid)
        val orderItemUid = order.itemUid!!

        return warehouseService
            .useWarranty(orderItemUid, request)
            .orElseThrow { throw WarrantyProcessException(orderItemUid, orderUid) }
    }

    override fun returnOrder(orderUid: UUID) {
        val order = orderService.getOrderByUid(orderUid)
        val orderItemUid = order.itemUid!!

        warehouseService.returnItem(orderItemUid)
        warrantyService.closeWarranty(orderItemUid)

        orderService.returnOrder(orderUid)
    }
}
