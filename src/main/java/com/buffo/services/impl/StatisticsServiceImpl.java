package com.buffo.services.impl;

import com.buffo.domain.Statistics;
import com.buffo.domain.Transaction;
import com.buffo.repository.TransactionRepository;
import com.buffo.services.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionRepository transactionRepository;

    public StatisticsServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Calculates statistical data across all currently available transactions
     *
     * @return The sum, avg, max, min and count - rounding up to 2 decimal places where necessary
     */
    public Statistics getStatistics() {
        Collection<Transaction> transactions = transactionRepository.findAllTransactions();

        return Statistics.builder()
                .sum(round(getSum(transactions)))
                .avg(round(getAvg(transactions)))
                .max(round(getMax(transactions)))
                .min(round(getMin(transactions)))
                .count(transactions.size())
                .build();
    }

    private BigDecimal getSum(Collection<Transaction> transactions) {
        return amountStream(transactions)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getAvg(Collection<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = getSum(transactions);
        BigDecimal size = BigDecimal.valueOf(transactions.size());
        return sum.divide(size, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getMax(Collection<Transaction> transactions) {
        return amountStream(transactions)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMin(Collection<Transaction> transactions) {
        return amountStream(transactions)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private static Stream<BigDecimal> amountStream(Collection<Transaction> transactions) {
        return transactions.stream().map(Transaction::getAmount);
    }
}
