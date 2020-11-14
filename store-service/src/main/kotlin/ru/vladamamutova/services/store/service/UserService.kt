package ru.vladamamutova.services.store.service

import ru.vladamamutova.services.store.domain.User
import java.util.*

interface UserService {
    fun getById(userUid: UUID): User
    fun existById(userUid: UUID): Boolean
}
