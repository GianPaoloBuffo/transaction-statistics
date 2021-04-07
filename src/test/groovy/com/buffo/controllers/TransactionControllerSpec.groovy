package com.buffo.controllers

import com.buffo.domain.Transaction
import com.buffo.services.impl.TransactionServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.buffo.utils.SpecUtils.createTransaction
import static com.buffo.utils.SpecUtils.now

class TransactionControllerSpec extends Specification {

    TransactionController controller = new TransactionController(Mock(TransactionServiceImpl))

    def "postTransaction returns 200 in case of success"() {
        given:
        Transaction transaction = createTransaction()

        when:
        ResponseEntity response = controller.postTransaction(transaction)

        then:
        1 * controller.transactionService.add(transaction) >> transaction
        response.statusCode == HttpStatus.CREATED
    }

    def "postTransaction returns 204 if transaction is older than 60 seconds"() {
        given:
        Transaction transaction = createTransaction(BigDecimal.ZERO, now().minusSeconds(60))

        when:
        ResponseEntity response = controller.postTransaction(transaction)

        then:
        0 * controller.transactionService.add(_)
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "postTransaction returns 422 if the transaction date is in the future"() {
        given:
        Transaction transaction = createTransaction(BigDecimal.ZERO, now().plusSeconds(60))

        when:
        ResponseEntity response = controller.postTransaction(transaction)

        then:
        0 * controller.transactionService.add(_)
        response.statusCode == HttpStatus.UNPROCESSABLE_ENTITY
    }
}
