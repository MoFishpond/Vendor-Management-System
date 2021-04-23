package com.hygym.vendor.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Yiming Gong
 * @date 2021/4/19 5:45 下午
 */
@Data
public class Stock {

    private String operator;

    private BigDecimal value;
}
