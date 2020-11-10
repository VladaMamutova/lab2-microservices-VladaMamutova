package ru.vladamamutova.storeservice.service

import org.springframework.stereotype.Service
import ru.vladamamutova.storeservice.domain.User
import ru.vladamamutova.storeservice.exception.UserNotFoundException
import ru.vladamamutova.storeservice.repository.UserRepository
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
