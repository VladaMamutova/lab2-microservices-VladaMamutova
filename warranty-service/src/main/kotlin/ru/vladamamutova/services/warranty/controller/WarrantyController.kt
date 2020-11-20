package ru.vladamamutova.services.warranty.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vladamamutova.services.warranty.model.ItemWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import ru.vladamamutova.services.warranty.model.WarrantyInfoResponse
import ru.vladamamutova.services.warranty.service.WarrantyService
import javax.validation.Valid
import java.util.*

@RestController
@RequestMapping("/api/v1/warranty")
class WarrantyController(
        @Autowired private val warrantyService: WarrantyService
) {
    @GetMapping("/{itemUid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getWarrantyInfo(@PathVariable itemUid: UUID): WarrantyInfoResponse {
        return warrantyService.getWarrantyInfo(itemUid)
    }

    @PostMapping(
            "/{itemUid}/warranty",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun requestForWarrantySolution(@PathVariable itemUid: UUID,
                                   @Valid @RequestBody request: ItemWarrantyRequest
    ): OrderWarrantyResponse {
        return warrantyService.requestForWarrantySolution(itemUid, request)
    }

    @PostMapping("/{itemUid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun startWarranty(@PathVariable itemUid: UUID) {
        return warrantyService.startWarranty(itemUid)
    }

    @DeleteMapping("/{itemUid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun closeWarranty(@PathVariable itemUid: UUID
    ) = warrantyService.closeWarranty(itemUid)
}
