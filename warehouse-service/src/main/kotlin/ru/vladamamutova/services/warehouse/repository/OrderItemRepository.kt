package ru.vladamamutova.services.warehouse.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.warehouse.domain.OrderItem

@Repository
interface OrderItemRepository: CrudRepository<OrderItem, Int> {
}
