package com.buffo.controllers;

import com.buffo.domain.Transaction;
import com.buffo.services.TransactionService;
import com.buffo.services.impl.TransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Adds a new {@link Transaction}
     *
     * @return Http Status Code <br>
     * 201 – in case of success <br>
     * 204 – if the transaction is older than 60 seconds <br>
     * 400 – if the JSON is invalid <br>
     * 422 – if any of the fields are not parsable or the transaction date is in the future
     */
    @PostMapping
    public ResponseEntity<String> postTransaction(@RequestBody Transaction transaction) {
        ZonedDateTime now = Instant.now().atZone(ZoneId.of("UTC"));

        if (transaction.getTimestamp().isBefore(now.minusSeconds(60))) {
            return ResponseEntity.noContent().build();
        }

        if (transaction.getTimestamp().isAfter(now)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        transactionService.add(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes all existing {@link Transaction}s
     *
     * @return Http Status Code 204
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAllTransactions() {
        transactionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
