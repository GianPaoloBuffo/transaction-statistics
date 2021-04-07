package com.buffo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class Statistics {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal sum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal avg;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal max;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal min;

    long count;
}
