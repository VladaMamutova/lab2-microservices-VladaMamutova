package ru.vladamamutova.services.warehouse.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.warehouse.domain.OrderItem
import java.util.*

@Repository
interface OrderItemRepository: CrudRepository<OrderItem, Int> {
    @Modifying
    @Query("update OrderItem oi set cancelled = true where oi.orderItemUid = :orderItemUid")
    fun cancelOrderItem(orderItemUid: UUID)
}
