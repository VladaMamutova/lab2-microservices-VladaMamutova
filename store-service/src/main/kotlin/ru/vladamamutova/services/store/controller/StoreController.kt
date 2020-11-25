package ru.vladamamutova.services.store.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vladamamutova.services.store.model.WarrantyRequest
import ru.vladamamutova.services.store.service.UserService
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/store")
class StoreController(@Autowired private val userService: UserService) {
    @GetMapping(
            "/{userUid}/orders", produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserOrders(@PathVariable userUid: UUID) {

    }

    @GetMapping(
            "/{userUid}/{orderUid}",
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserOrderById(@PathVariable userUid: UUID,
                         @PathVariable orderUid: UUID
    ) {

    }

    @PostMapping(
            "/{userUid}/{orderUid}/warranty",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun requestWarranty(@PathVariable userUid: UUID,
                        @PathVariable orderUid: UUID,
                        @Valid @RequestBody request: WarrantyRequest
    ) {

    }
}
