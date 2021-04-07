package com.buffo.domain

import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class TransactionListSpec extends Specification {

    def "TransactionList only keeps transactions for the last 60 seconds"() {
        given:
        ZonedDateTime now = Instant.now().atZone(ZoneId.of('UTC'))
        Transaction t1 = new Transaction(timestamp: now.minusSeconds(0))
        Transaction t2 = new Transaction(timestamp: now.minusSeconds(20))
        Transaction t3 = new Transaction(timestamp: now.minusSeconds(40))
        Transaction t4 = new Transaction(timestamp: now.minusSeconds(59))
        Transaction t5 = new Transaction(timestamp: now.minusSeconds(60))
        Transaction t6 = new Transaction(timestamp: now.minusSeconds(61))

        and:
        TransactionList transactionList = new TransactionList()
        transactionList.add(t1)
        transactionList.add(t2)
        transactionList.add(t3)
        transactionList.add(t4)
        transactionList.add(t5)
        transactionList.add(t6)

        when:
        Collection<Transaction> result = transactionList.getAll()

        then:
        result.size() == 4
        result.containsAll(t1, t2, t3, t4)
        !result.contains(t5)
        !result.contains(t6)
    }
}
