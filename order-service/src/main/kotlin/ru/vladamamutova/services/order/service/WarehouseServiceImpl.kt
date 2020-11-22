package ru.vladamamutova.services.order.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.order.model.OrderWarrantyRequest
import ru.vladamamutova.services.order.model.OrderWarrantyResponse
import java.util.*

@Service
class WarehouseServiceImpl : WarehouseService {
    override fun useWarranty(orderUid: UUID, request: OrderWarrantyRequest
    ): OrderWarrantyResponse {
        TODO("Not yet implemented")
    }
}
