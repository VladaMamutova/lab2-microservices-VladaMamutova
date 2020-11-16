package ru.vladamamutova.services.warehouse.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vladamamutova.services.warehouse.domain.OrderItem
import ru.vladamamutova.services.warehouse.exception.ItemNotAvailableException
import ru.vladamamutova.services.warehouse.exception.ItemNotFoundException
import ru.vladamamutova.services.warehouse.model.ItemInfoResponse
import ru.vladamamutova.services.warehouse.model.OrderItemRequest
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import ru.vladamamutova.services.warehouse.repository.ItemRepository
import ru.vladamamutova.services.warehouse.repository.OrderItemRepository
import java.util.*


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

    override fun getItemInfo(orderItemUid: UUID): ItemInfoResponse {
        val item = itemRepository.findByOrderItemUid(orderItemUid).orElseThrow {
            ItemNotFoundException(orderItemUid)
        }

        return ItemInfoResponse(item.model!!, item.size!!.name)
    }

    override fun takeItem(request: OrderItemRequest): OrderItemResponse {
        val item = itemRepository
            .findByModelAndSize(request.model, request.size)
            .orElseThrow {
                ItemNotFoundException(request.model, request.size)
            }

        if (item.availableCount > 1) {
            throw ItemNotAvailableException(item.model, item.size!!.name)
        }

        itemRepository.takeOneItem(item.id!!)

        val orderItem = OrderItem(UUID.randomUUID(), request.orderUid, item)
        orderItemRepository.save(orderItem)

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
    }
}
