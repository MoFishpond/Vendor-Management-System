package com.hygym.vendor.entity;

import lombok.Data;

/**
 * @author Yiming Gong
 * @date 2021/4/19 5:42 下午
 */
@Data
public class ProductFilter {

    private Long shopId;

    private Long productId;

    private String name;

    private String keyword;

    private Stock stock;
}
