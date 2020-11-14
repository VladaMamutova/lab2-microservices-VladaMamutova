package ru.vladamamutova.services.store.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.store.domain.User
import ru.vladamamutova.services.store.exception.UserNotFoundException
import ru.vladamamutova.services.store.repository.UserRepository
import java.util.*

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {
    override fun getById(userUid: UUID): User {
        return userRepository
            .findByUserUid(userUid)
            .orElseThrow { throw UserNotFoundException(userUid) }
    }

    override fun existById(userUid: UUID): Boolean {
        return userRepository.findByUserUid(userUid).isPresent
    }
}
