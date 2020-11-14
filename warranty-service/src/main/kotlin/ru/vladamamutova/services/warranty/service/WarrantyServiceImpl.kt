package ru.vladamamutova.services.warranty.service

import org.springframework.stereotype.Service
import ru.vladamamutova.services.warranty.domain.Warranty
import ru.vladamamutova.services.warranty.exception.WarrantyNotFoundException
import ru.vladamamutova.services.warranty.model.ItemWarrantyRequest
import ru.vladamamutova.services.warranty.model.OrderWarrantyResponse
import ru.vladamamutova.services.warranty.model.WarrantyDecision
import ru.vladamamutova.services.warranty.model.WarrantyStatus
import ru.vladamamutova.services.warranty.repository.WarrantyRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class WarrantyServiceImpl(private val warrantyRepository: WarrantyRepository) :
        WarrantyService {
    override fun getWarrantyByItemUid(itemUid: UUID): Warranty {
        return warrantyRepository
            .findByItemUid(itemUid)
            .orElseThrow { throw WarrantyNotFoundException(itemUid) }
    }

    override fun checkWarranty(itemUid: UUID, request: ItemWarrantyRequest
    ): OrderWarrantyResponse {
        val warranty = getWarrantyByItemUid(itemUid)

        var decision = WarrantyDecision.REFUSED

        if (isWarrantyActive(warranty) && warranty.status == WarrantyStatus.ON_WARRANTY) {
            decision = if (request.availableCount > 0) {
                WarrantyDecision.RETURN
            } else WarrantyDecision.FIXING
        }

        warranty.comment = request.reason
        warranty.status =
                if (decision == WarrantyDecision.REFUSED)
                    WarrantyStatus.REMOVED_FROM_WARRANTY
                else WarrantyStatus.USE_WARRANTY

        warrantyRepository.save(warranty)

        return OrderWarrantyResponse(
                decision = decision.name,
                warrantyDate = warranty.warrantyDate!!.toString("dd.MM.yyyy HH:mm:ss")
        )
    }

    override fun startWarranty(itemUid: UUID) {
        warrantyRepository.save(Warranty(itemUid))
    }

    override fun closeWarranty(itemUid: UUID) {
        warrantyRepository.deleteWarrantyByItemUid(itemUid)
    }

    private fun isWarrantyActive(warranty: Warranty): Boolean {
        return warranty.warrantyDate!!.isAfter(
                LocalDateTime.now().minus(1, ChronoUnit.MONTHS)
        )
    }

    fun LocalDateTime.toString(format: String): String {
        return this.format(DateTimeFormatter.ofPattern(format))
    }
}