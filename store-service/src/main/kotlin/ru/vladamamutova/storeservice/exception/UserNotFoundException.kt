package ru.vladamamutova.storeservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(userUid: UUID) :
        RuntimeException("User $userUid not found.") {
}
