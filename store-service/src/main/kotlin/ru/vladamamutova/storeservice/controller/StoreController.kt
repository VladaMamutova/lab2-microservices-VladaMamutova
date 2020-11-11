package ru.vladamamutova.storeservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vladamamutova.storeservice.service.UserService
import java.util.*

@RestController
@RequestMapping("/api/v1/store")
class StoreController(@Autowired private val userService: UserService) {
    @GetMapping("/{userUid}/orders", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserOrders(@PathVariable userUid: UUID) {

    }

    @GetMapping("/{userUid}/{orderUid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserOrderById(@PathVariable userUid: UUID, @PathVariable orderUid: UUID) {

    }
}
