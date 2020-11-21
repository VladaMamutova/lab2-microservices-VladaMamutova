package ru.vladamamutova.services.store.domain

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

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + name.hashCode()
        result = 31 * result + userUid.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that: User = other as User

        return Objects.equals(name, that.name) &&
                Objects.equals(userUid, that.userUid)
    }

    override fun toString(): String {
        return "User (id = $id, name = $name, userUid = $userUid)"
    }
}
