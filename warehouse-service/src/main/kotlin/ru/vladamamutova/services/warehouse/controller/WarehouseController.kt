package ru.vladamamutova.services.warehouse.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import ru.vladamamutova.services.warehouse.model.OrderItemRequest
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import ru.vladamamutova.services.warehouse.model.OrderWarrantyRequest
import ru.vladamamutova.services.warehouse.service.WarehouseService
import ru.vladamamutova.services.warehouse.service.WarrantyService
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/warehouse")
class WarehouseController(
        @Autowired private val warehouseService: WarehouseService,
        @Autowired private val warrantyService: WarrantyService
) {

    @GetMapping(
            "/{orderItemUid}",
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getItemInfo(@Valid @PathVariable orderItemUid: UUID): ItemInfoResponse {
        return warehouseService.getItemInfo(orderItemUid)
    }

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun takeItem(@Valid @RequestBody request: OrderItemRequest
    ): OrderItemResponse {
        return warehouseService.takeItem(request)
    }

    @PostMapping(
            "/{orderItemUid}/warranty",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun requestForWarrantySolution(@PathVariable orderItemUid: UUID,
                                   @Valid @RequestBody request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        return warrantyService.requestForWarrantySolution(orderItemUid, request)
    }

    @DeleteMapping("/orderItemUid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnItem(orderItemUid: UUID) {
        warehouseService.returnItem(orderItemUid)
    }
}
