package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Yiming Gong
 * @date 2021/4/22 10:26 下午
 */
@Data
@AllArgsConstructor
public class OrderDetail {
    private Long orderId;
    private Date date;
    private Long customerId;
    private String country;
    private String products;
    private BigDecimal amount;
}
