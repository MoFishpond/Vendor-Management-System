package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @author Yiming Gong
 * @date 2021/4/22 10:26 下午
 */
@Data
@AllArgsConstructor
public class OrderDetail {
    private Long orderId;
    private DateTime date;
    private Long customerId;
    private String country;
    private String products;
    private BigDecimal amount;
}
