package ru.vladamamutova.services.warranty.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.vladamamutova.services.warranty.domain.Warranty
import java.util.*

@Repository
interface WarrantyRepository : CrudRepository<Warranty, Int> {
    fun findByItemUid(itemUid: UUID): Optional<Warranty>

    @Modifying
    @Query("delete from Warranty w where w.itemUid = :itemUid")
    fun deleteWarrantyByItemUid(@Param("itemUid") itemUid: UUID)
}
