package ru.vladamamutova.services.warehouse.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.warehouse.domain.Item

@Repository
interface ItemRepository: CrudRepository<Item, Int> {
}
