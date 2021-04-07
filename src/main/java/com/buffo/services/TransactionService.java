package com.buffo.services;

import com.buffo.domain.Transaction;

public interface TransactionService {

    Transaction add(Transaction transaction);

    void deleteAll();
}
