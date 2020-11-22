package ru.vladamamutova.services.order.domain

import ru.vladamamutova.services.order.model.PaymentStatus
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders", indexes = [
    Index(name = "idx_orders_order_uid", columnList = "order_uid", unique = true)
])
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "item_uid", nullable = false)
    var itemUid: UUID? = null

    @Column(name = "order_date", columnDefinition = "TIMESTAMP", nullable = false)
    var orderDate: LocalDateTime? = null

    @Column(name = "order_uid", nullable = false, unique = true)
    var orderUid: UUID? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: PaymentStatus? = null

    @Column(name = "user_uid", nullable = false)
    var userUid: UUID? = null

    constructor()

    constructor(itemUid: UUID?, orderUid: UUID?, userUid: UUID?)
            : this(
            itemUid,
            LocalDateTime.now(),
            orderUid,
            PaymentStatus.PAID,
            userUid
    )

    constructor(itemUid: UUID?, orderDate: LocalDateTime?,
                orderUid: UUID?, status: PaymentStatus?, userUid: UUID?
    ) {
        this.itemUid = itemUid
        this.orderDate = orderDate
        this.orderUid = orderUid
        this.status = status
        this.userUid = userUid
    }

    constructor(id: Int?, itemUid: UUID?, orderDate: LocalDateTime?,
                orderUid: UUID?, status: PaymentStatus?, userUid: UUID?
    ) {
        this.id = id
        this.itemUid = itemUid
        this.orderDate = orderDate
        this.orderUid = orderUid
        this.status = status
        this.userUid = userUid
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + itemUid.hashCode()
        result = 31 * result + orderUid.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that: Order = other as Order

        return Objects.equals(itemUid, that.itemUid) &&
                Objects.equals(orderUid, that.orderUid)
    }

    override fun toString(): String {
        return "Order (id = $id, userUid = $userUid, itemUid = $itemUid, " +
                "orderUid = $orderUid, orderDate = $orderDate, status = $status)"
    }
}
