package com.buffo.domain;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A container which stores {@link Transaction}s for 60 seconds
 */
public class TransactionList {

    private static final int LIFETIME_IN_SECONDS = 60;

    private final Map<Transaction, Instant> expiringMap = new ConcurrentHashMap<>();

    public Transaction add(Transaction transaction) {
        cleanup();
        expiringMap.put(transaction, transaction.getTimestamp().toInstant());
        return transaction;
    }

    public Collection<Transaction> getAll() {
        cleanup();
        return expiringMap.keySet();
    }

    public void clear() {
        expiringMap.clear();
    }

    private void cleanup() {
        for (Transaction key : expiringMap.keySet()) {
            Instant timeBoundary = expiringMap.get(key).plusSeconds(LIFETIME_IN_SECONDS);
            if (Instant.now().isAfter(timeBoundary)) {
                expiringMap.remove(key);
            }
        }
    }
}
