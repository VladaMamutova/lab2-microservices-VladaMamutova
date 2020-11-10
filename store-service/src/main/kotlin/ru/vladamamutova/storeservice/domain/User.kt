package ru.vladamamutova.storeservice.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users", indexes = [
    Index(columnList = "name", name = "idx_user_name", unique = true),
    Index(columnList = "user_uid", name = "idx_user_user_uid", unique = true)
])
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false, unique = true, length = 255)
    var name: String? = null

    @Column(name = "user_uid", nullable = false, unique = true)
    var userUid: UUID? = null

    override fun toString(): String {
        return "User (id = $id, name = $name, userUid = $userUid)"
    }
}
