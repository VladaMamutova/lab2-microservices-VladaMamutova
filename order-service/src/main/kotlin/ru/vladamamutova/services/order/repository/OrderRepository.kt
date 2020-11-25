package ru.vladamamutova.services.order.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.order.domain.Order
import java.util.*

@Repository
interface OrderRepository : CrudRepository<Order, Int> {
    fun findByUserUidAndOrderUid(userUid: UUID, orderUid: UUID): Optional<Order>
    fun findByUserUid(userUid: UUID): List<Order>
    fun findByOrderUid(orderUid: UUID): Optional<Order>

    @Modifying
    @Query("delete from Order o where o.orderUid = :orderUid")
    fun deleteByOrderUid(orderUid: UUID): Int
}
