package ru.vladamamutova.services.order.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vladamamutova.services.order.domain.Order
import ru.vladamamutova.services.order.exception.OrderNotFoundException
import ru.vladamamutova.services.order.model.OrderResponse
import ru.vladamamutova.services.order.repository.OrderRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
@Transactional
class OrderServiceImpl(private val orderRepository: OrderRepository) :
        OrderService {
    override fun getUserOrder(userUid: UUID, orderUid: UUID
    ): OrderResponse {
        val order = orderRepository
            .findByUserUidAndOrderUid(orderUid, userUid)
            .orElseThrow { throw OrderNotFoundException(orderUid, userUid) }

        return order.toOrderResponse()
    }

    override fun getUserOrders(userUid: UUID): List<OrderResponse> {
        return orderRepository
            .findByUserUid(userUid)
            .map { it.toOrderResponse() }
    }

    override fun createOrder(itemUid: UUID, orderUid: UUID, userUid: UUID) {
        orderRepository.save(Order(itemUid, orderUid, userUid))
    }

    override fun returnOrder(orderUid: UUID) {
        orderRepository.deleteByOrderUid(orderUid)
    }

    override fun getOrderByUid(orderUid: UUID): Order {
        return orderRepository
            .findByOrderUid(orderUid)
            .orElseThrow { throw OrderNotFoundException(orderUid) }
    }

    private fun Order.toOrderResponse(): OrderResponse {
        return OrderResponse(
                this.itemUid!!,
                this.orderDate!!.format(),
                this.orderUid!!,
                this.status!!
        )
    }

    private fun LocalDateTime.format(): String {
        return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
    }
}
