package ru.vladamamutova.services.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vladamamutova.services.order.model.*
import ru.vladamamutova.services.order.service.OrderManagementService
import ru.vladamamutova.services.order.service.OrderService
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(@Autowired private val orderService: OrderService,
                      @Autowired private val orderManagementService: OrderManagementService
) {
    @GetMapping(
            "/{userUid}/{orderUid}",
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserOrder(@PathVariable userUid: UUID, @PathVariable orderUid: UUID
    ): OrderResponse {
        return orderService.getUserOrder(userUid, orderUid)
    }

    @GetMapping("/{userUid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserOrders(@PathVariable userUid: UUID): List<OrderResponse> {
        return orderService.getUserOrders(userUid)
    }

    @PostMapping(
            "/{userUid}",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun makeOrder(@PathVariable userUid: UUID,
                  @Valid @RequestBody request: CreateOrderRequest
    ): CreateOrderResponse {
        return orderManagementService.makeOrder(userUid, request)
    }

    @PostMapping(
            "/{orderUid}/warranty",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun useWarranty(@PathVariable orderUid: UUID,
                    @Valid @RequestBody request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        return orderManagementService.useWarranty(orderUid, request)
    }

    @DeleteMapping("/{orderUid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnOrder(@PathVariable orderUid: UUID) {
        orderManagementService.returnOrder(orderUid)
    }
}
