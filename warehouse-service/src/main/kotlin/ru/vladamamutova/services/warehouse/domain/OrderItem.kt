package ru.vladamamutova.services.warehouse.domain

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "order_item", indexes = [
    Index(name = "idx_order_item_order_item_uid", columnList = "order_item_uid",
          unique = true),
    Index(name = "idx_order_item_item_id", columnList = "item_id"),
    Index(name = "idx_order_item_order_uid", columnList = "order_uid")
])
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var cancelled: Boolean = false

    @Column(name = "order_item_uid", nullable = false, unique = true)
    var orderItemUid: UUID? = null

    @Column(name = "order_uid", nullable = false)
    var orderUid: UUID? = null

    // При FetchType.LAZY объект загружается в память только при обращении к нему.
    // При этом требуется, чтобы соединение с базой (или транзакция) сохранялись
    // (объект attached). Поэтому для работы с lazy объектами тратится
    // больше ресурсов на поддержку соединений.
    // При FetchType.EAGER объект сразу загружается в память (если коллекция
    // объектов - следить, чтобы вся база в память не загрузилась!)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "item_id",
            foreignKey = ForeignKey(name = "fk_order_item_item_id")
    )
    var item: Item? = null

    constructor()

    constructor(orderItemUid: UUID?, orderUid: UUID?, item: Item?) {
        this.orderItemUid = orderItemUid
        this.orderUid = orderUid
        this.item = item
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        if (orderItemUid != other.orderItemUid) return false
        if (orderUid != other.orderUid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orderItemUid?.hashCode() ?: 0
        result = 31 * result + (orderUid?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "OrderItem(id=$id, cancelled=$cancelled, " +
                "orderItemUid=$orderItemUid, orderUid=$orderUid, item=$item)"
    }
}
