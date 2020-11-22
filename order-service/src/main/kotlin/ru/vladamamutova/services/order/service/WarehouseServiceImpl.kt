package ru.vladamamutova.services.order.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import ru.vladamamutova.services.warehouse.model.OrderItemResponse
import java.util.*

@Service
class WarehouseServiceImpl : WarehouseService {
    override fun takeItem(orderUid: UUID, model: String, size: String
    ): Optional<OrderItemResponse> {
        TODO("Not yet implemented")
    }

    override fun useWarranty(itemUid: UUID, request: OrderWarrantyRequest
    ): Optional<OrderWarrantyResponse> {
        TODO("Not yet implemented")
    }

    override fun returnItem(itemUid: UUID) {
        TODO("Not yet implemented")
    }
}
