package com.buffo.repository;

import com.buffo.domain.TransactionList;
import com.buffo.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TransactionRepository {

    private final TransactionList transactions = new TransactionList();

    public Transaction addTransaction(Transaction transaction) {
        return transactions.add(transaction);
    }

    public Collection<Transaction> findAllTransactions() {
        return transactions.getAll();
    }

    public void deleteAllTransactions() {
        transactions.clear();
    }
}
