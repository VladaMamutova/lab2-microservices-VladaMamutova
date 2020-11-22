package ru.vladamamutova.services.warranty.domain

import ru.vladamamutova.services.warranty.model.WarrantyStatus
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "warranty", indexes = [
    Index(name = "idx_warranty_item_uid", columnList = "item_uid", unique = true)
])
class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(length = 1024)
    var comment: String? = null

    @Column(name = "item_uid", nullable = false, unique = true)
    var itemUid: UUID? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: WarrantyStatus? = null

    @Column(name = "warranty_date", columnDefinition = "TIMESTAMP", nullable = false)
    var warrantyDate: LocalDateTime? = null

    constructor()

    constructor(itemUid: UUID) : this(
            itemUid,
            WarrantyStatus.ON_WARRANTY,
            LocalDateTime.now()
    )

    constructor(itemUid: UUID?, status: WarrantyStatus?,
                warrantyDate: LocalDateTime?
    ) {
        this.itemUid = itemUid
        this.status = status
        this.warrantyDate = warrantyDate
    }

    constructor(id: Int?, comment: String?, itemUid: UUID?,
                status: WarrantyStatus?, warrantyDate: LocalDateTime?
    ) {
        this.id = id
        this.comment = comment
        this.itemUid = itemUid
        this.status = status
        this.warrantyDate = warrantyDate
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Warranty

        if (itemUid != other.itemUid) return false
        if (status != other.status) return false
        if (warrantyDate != other.warrantyDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = itemUid?.hashCode() ?: 0
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (warrantyDate?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Warranty(id=$id, comment=$comment, itemUid=$itemUid, " +
                "status=$status, warrantyDate=$warrantyDate)"
    }
}