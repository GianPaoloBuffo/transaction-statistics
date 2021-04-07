package com.buffo.utils

import com.buffo.domain.Transaction

import java.math.RoundingMode
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class SpecUtils {

    static Transaction createTransaction(BigDecimal amount = BigDecimal.ZERO, ZonedDateTime timestamp = now()) {
        new Transaction(timestamp: timestamp, amount: amount)
    }

    static round(BigDecimal value) {
        value.setScale(2, RoundingMode.HALF_UP)
    }

    static ZonedDateTime now() {
        Instant.now().atZone(ZoneId.of('UTC'))
    }
}
