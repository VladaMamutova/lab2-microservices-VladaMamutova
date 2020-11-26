package ru.vladamamutova.services.store.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.vladamamutova.services.store.model.PurchaseRequest
import ru.vladamamutova.services.store.model.UserOrderResponse
import ru.vladamamutova.services.store.model.WarrantyRequest
import ru.vladamamutova.services.store.model.WarrantyResponse
import ru.vladamamutova.services.store.service.StoreService
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/store")
class StoreController(@Autowired private val storeService: StoreService) {
    @GetMapping(
            "/{userUid}/orders", produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserOrders(@PathVariable userUid: UUID) : List<UserOrderResponse> {
        return storeService.getUserOrders(userUid)
    }

    @GetMapping(
            "/{userUid}/{orderUid}",
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserOrder(@PathVariable userUid: UUID,
                     @PathVariable orderUid: UUID
    ): UserOrderResponse {
        return storeService.getUserOrderById(userUid, orderUid)
    }

    @PostMapping(
            "/{userUid}/{orderUid}/warranty",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun requestWarranty(@PathVariable userUid: UUID,
                        @PathVariable orderUid: UUID,
                        @Valid @RequestBody request: WarrantyRequest
    ): WarrantyResponse {
        return storeService.requestWarranty(userUid, orderUid, request)
    }

    @PostMapping(
            "/{userUid}/purchase",
            consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun makePurchase(@PathVariable userUid: UUID,
                     @Valid @RequestBody request: PurchaseRequest
    ): ResponseEntity<Void> {
        val orderUid: UUID = storeService.makePurchase(userUid, request)
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{orderUid}")
                    .buildAndExpand(orderUid)
                    .toUri()
        ).build()
    }

    @DeleteMapping("/{userUid}/{orderUid}/refund")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun refundPurchase(@PathVariable userUid: UUID, @PathVariable orderUid: UUID) {
        storeService.refundPurchase(userUid, orderUid)
    }
}
