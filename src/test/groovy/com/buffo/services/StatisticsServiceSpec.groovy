package com.buffo.services

import com.buffo.domain.Statistics
import com.buffo.domain.Transaction
import com.buffo.repository.TransactionRepository
import com.buffo.services.impl.StatisticsServiceImpl
import spock.lang.Specification

import static com.buffo.utils.SpecUtils.createTransaction
import static com.buffo.utils.SpecUtils.round

class StatisticsServiceSpec extends Specification {

    StatisticsServiceImpl service = new StatisticsServiceImpl(Mock(TransactionRepository))

    def "getStatistics returns the correct (sum, avg, max, min, count) rounded up to 2 decimal places"() {
        given:
        Transaction t1 = createTransaction(new BigDecimal(19.23d))
        Transaction t2 = createTransaction(new BigDecimal(0d))
        Transaction t3 = createTransaction(new BigDecimal(-21d))
        Transaction t4 = createTransaction(new BigDecimal(12.562d))
        Transaction t5 = createTransaction(new BigDecimal(0.01d))
        Collection<Transaction> transactions = [t1, t2, t3, t4, t5]

        when:
        Statistics result = service.getStatistics()

        then:
        1 * service.transactionRepository.findAllTransactions() >> transactions

        result.sum == round(new BigDecimal(10.8d))
        result.avg == round(new BigDecimal(2.16d))
        result.max == round(new BigDecimal(19.23d))
        result.min == round(new BigDecimal(-21d))
        result.count == transactions.size()
    }
}
