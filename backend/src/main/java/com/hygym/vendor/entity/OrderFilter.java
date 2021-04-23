package com.hygym.vendor.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Yiming Gong
 * @date 2021/4/19 8:35 下午
 */
@Data
public class OrderFilter {

    private Integer shopId;

    private Long customerId;

    private Long productId;

    private Long from;

    private Long to;

    private BigDecimal money;
}
