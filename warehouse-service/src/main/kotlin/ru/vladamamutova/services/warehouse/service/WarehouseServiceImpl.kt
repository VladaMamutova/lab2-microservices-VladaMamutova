package ru.vladamamutova.services.warehouse.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vladamamutova.services.warehouse.domain.OrderItem
import ru.vladamamutova.services.warehouse.exception.ItemNotAvailableException
import ru.vladamamutova.services.warehouse.exception.ItemNotFoundException
import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import ru.vladamamutova.services.warehouse.model.OrderItemRequest
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import ru.vladamamutova.services.warehouse.model.Size
import ru.vladamamutova.services.warehouse.repository.ItemRepository
import ru.vladamamutova.services.warehouse.repository.OrderItemRepository
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
// The @Transactional annotation supports the following configuration:
// - the Propagation Type of the transaction (default: Propagation.REQUIRED)
// - the Isolation Level of the transaction (Isolation.DEFAULT)
// - a Timeout for the operation wrapped by the transaction (default: 60)
// - a readOnly flag (a hint for the persistence provider that the transaction should be read only)
// - the Rollback rules for the transaction (!) (default: RunTimeException.class)
@Transactional
class WarehouseServiceImpl(
        private val itemRepository: ItemRepository,
        private val orderItemRepository: OrderItemRepository
) : WarehouseService {
    private val logger = LoggerFactory.getLogger(WarehouseServiceImpl::class.java)

    override fun getItemInfo(orderItemUid: UUID): ItemInfoResponse {
        val item = itemRepository.findByOrderItemUid(orderItemUid).orElseThrow {
            throw ItemNotFoundException(orderItemUid)
        }

        return ItemInfoResponse(item.model!!, item.size!!.name)
    }

    override fun takeItem(request: OrderItemRequest): OrderItemResponse {
        val orderUid = request.orderUid
        val model = request.model
        val size = Size.valueOf(request.size)
        logger.info("Take item (model = $model, size = $size) for order '$orderUid'")

        val item = itemRepository
            .findByModelAndSize(model, size)
            .orElseThrow {
                throw ItemNotFoundException(model, size.name)
            }

        if (item.availableCount < 1) {
            throw ItemNotAvailableException(item.model, item.size!!.name)
        }

        val orderItem = OrderItem(UUID.randomUUID(), request.orderUid, item)
        orderItemRepository.save(orderItem)
        logger.info("Create $orderItem for order '$orderUid'")

        itemRepository.takeOneItem(item.id!!)
        logger.info("$item was taken for order '$orderUid'")

        return OrderItemResponse(
                orderItem.orderItemUid!!,
                orderItem.orderUid!!,
                item.model!!,
                item.size!!
        )
    }

    override fun returnItem(orderItemUid: UUID) {
        itemRepository.returnOneItem(orderItemUid)
        orderItemRepository.cancelOrderItem(orderItemUid)
        logger.info("Item $orderItemUid was returned")
    }

    override fun getItemAvailableCount(orderItemUid: UUID): Int {
        val item = itemRepository
            .findByOrderItemUid(orderItemUid)
            .orElseThrow { throw ItemNotFoundException(orderItemUid) }

        return item.availableCount
    }
}
