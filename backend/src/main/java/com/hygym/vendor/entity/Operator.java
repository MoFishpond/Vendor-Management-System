package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;

/**
 * @author Yiming Gong
 * @date 2021/4/19 5:46 下午
 */
@AllArgsConstructor
public enum Operator {
    GE("ge"),
    LE("le"),
    EQ("eq");
    private String op;
}
