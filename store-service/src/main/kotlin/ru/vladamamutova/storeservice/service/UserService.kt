package ru.vladamamutova.storeservice.service

import ru.vladamamutova.storeservice.domain.User
import java.util.*

interface UserService {
    fun getById(userUid: UUID): User
    fun existById(userUid: UUID): Boolean
}
