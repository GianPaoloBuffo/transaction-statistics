package com.buffo.services.impl;

import com.buffo.domain.Transaction;
import com.buffo.repository.TransactionRepository;
import com.buffo.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction add(Transaction transaction) {
        return transactionRepository.addTransaction(transaction);
    }

    public void deleteAll() {
        transactionRepository.deleteAllTransactions();
    }
}
