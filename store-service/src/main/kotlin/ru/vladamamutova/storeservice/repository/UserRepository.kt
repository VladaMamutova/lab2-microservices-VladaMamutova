package ru.vladamamutova.storeservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.storeservice.domain.User
import java.util.*

@Repository
interface UserRepository: CrudRepository<UserRepository, Int> {
    fun findByUserUid(userUid: UUID): Optional<User>
}
