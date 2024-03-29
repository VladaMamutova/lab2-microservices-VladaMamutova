package ru.vladamamutova.services.warehouse.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.warehouse.domain.Item
import ru.vladamamutova.services.warehouse.model.Size
import java.util.*

@Repository
interface ItemRepository : CrudRepository<Item, Int> {
    @Query("select oi.item from OrderItem oi where oi.orderItemUid = :orderItemUid")
    fun findByOrderItemUid(@Param("orderItemUid") orderItemUid: UUID
    ): Optional<Item>

    @Query("select i from Item i where i.model = :model and i.size = :size")
    // = @Query("select i from Item i where i.model = ?1 and i.size = ?2")
    fun findByModelAndSize(model: String, size: Size): Optional<Item>

    @Modifying
    @Query("update Item i set i.availableCount = i.availableCount - 1 where i.id = :id")
    fun takeOneItem(id: Int)

    @Modifying
    @Query("update Item i set i.availableCount = i.availableCount + 1 " +
                    "where i.id = (select oi.item.id from OrderItem oi where oi.orderItemUid = :orderItemUid)"
    )
    fun returnOneItem(orderItemUid: UUID)
}
