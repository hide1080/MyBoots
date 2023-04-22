package co.jp.groves.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

public record Order(Integer orderId, Integer accountId, OrderLines orderLines, LocalDate orderDate)
        implements Serializable {}
