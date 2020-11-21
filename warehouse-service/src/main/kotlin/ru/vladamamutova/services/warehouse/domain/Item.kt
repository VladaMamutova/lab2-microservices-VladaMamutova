package ru.vladamamutova.services.warehouse.domain

import ru.vladamamutova.services.warehouse.model.Size
import javax.persistence.*

@Entity
@Table(name = "items")
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "available_count", nullable = false)
    var availableCount: Int = 0

    @Column(name = "model", nullable = false, length = 255)
    var model: String? = null

    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    var size: Size? = null

    constructor()

    constructor(model: String?, size: Size?) {
        this.model = model
        this.size = size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (model != other.model) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = model?.hashCode() ?: 0
        result = 31 * result + (size?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Item(id=$id, availableCount=$availableCount, model=$model, " +
                "size=$size)"
    }
}
