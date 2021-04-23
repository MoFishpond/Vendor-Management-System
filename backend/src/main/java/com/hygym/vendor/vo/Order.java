package com.hygym.vendor.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 6:01 下午
 */
@Data
@AllArgsConstructor
public class Order {
    private Long orderId;

    private Long customerId;

    private String area;

    private List<Detail> details;

    private BigDecimal money;
}
