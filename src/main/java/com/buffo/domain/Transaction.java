package com.buffo.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
public class Transaction {

    private BigDecimal amount;
    private ZonedDateTime timestamp;
}
