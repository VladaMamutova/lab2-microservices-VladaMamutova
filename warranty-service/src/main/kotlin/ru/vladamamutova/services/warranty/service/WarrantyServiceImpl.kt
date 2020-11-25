package ru.vladamamutova.services.warranty.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vladamamutova.services.warranty.domain.Warranty
import ru.vladamamutova.services.warranty.exception.WarrantyNotFoundException
import ru.vladamamutova.services.warranty.model.*
import ru.vladamamutova.services.warranty.repository.WarrantyRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Service
@Transactional
class WarrantyServiceImpl(private val warrantyRepository: WarrantyRepository) :
        WarrantyService {

    private val logger = LoggerFactory.getLogger(WarrantyServiceImpl::class.java)

    override fun getWarrantyByItemUid(itemUid: UUID): Warranty {
        return warrantyRepository
            .findByItemUid(itemUid)
            .orElseThrow { throw WarrantyNotFoundException(itemUid) }
    }

    override fun getWarrantyInfo(itemUid: UUID): WarrantyInfoResponse {
        val warranty = getWarrantyByItemUid(itemUid)
        return WarrantyInfoResponse(
                warranty.itemUid!!,
                warranty.status.toString(),
                warranty.warrantyDate!!.format()
        )
    }

    override fun requestForWarrantySolution(itemUid: UUID,
                                            request: ItemWarrantyRequest
    ): OrderWarrantyResponse {
        logger.info(
                "Request for warranty solution (reason: {}) for item '{}'",
                request.reason, itemUid
        )

        val warranty = getWarrantyByItemUid(itemUid)

        var decision = WarrantyDecision.REFUSED
        if (isWarrantyActive(warranty) && warranty.status == WarrantyStatus.ON_WARRANTY) {
            decision = if (request.availableCount > 0) {
                WarrantyDecision.RETURN
            } else WarrantyDecision.FIXING
        }
        logger.info(
                "Warranty decision on item '{}' is {} (count: {}, status: {})",
                itemUid, decision, request.availableCount, warranty.status
        )

        warranty.comment = request.reason
        warranty.status =
                if (decision == WarrantyDecision.REFUSED)
                    WarrantyStatus.REMOVED_FROM_WARRANTY
                else WarrantyStatus.USE_WARRANTY

        warrantyRepository.save(warranty)
        logger.info(
                "Update warranty status {} for itemUid '{}'",
                warranty.status,
                warranty.itemUid
        )

        return OrderWarrantyResponse(
                decision.name,
                warranty.warrantyDate!!.format()
        )
    }

    override fun startWarranty(itemUid: UUID) {
        warrantyRepository.save(Warranty(itemUid))
        logger.info("Start warranty for item '{}'", itemUid)
    }

    override fun closeWarranty(itemUid: UUID) {
        val deleted = warrantyRepository.deleteByItemUid(itemUid)
        if (deleted > 1) {
            logger.info("Remove warranty for item '{}'", itemUid)
        }
    }

    private fun isWarrantyActive(warranty: Warranty): Boolean {
        return warranty.warrantyDate!!.isAfter(
                LocalDateTime.now().minus(1, ChronoUnit.MONTHS)
        )
    }

    private fun LocalDateTime.format(): String {
        return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
    }
}
