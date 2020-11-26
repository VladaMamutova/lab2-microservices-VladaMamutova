package ru.vladamamutova.services.store.exception

import java.lang.RuntimeException
import java.util.*

class UserNotFoundException(userUid: UUID) :
        RuntimeException("User $userUid not found") {
}
