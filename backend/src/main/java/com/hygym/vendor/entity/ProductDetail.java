package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;

/**
 * @author Yiming Gong
 * @date 2021/4/22 10:03 下午
 */
@Data
@AllArgsConstructor
public class ProductDetail {
    private Long productId;
    private String name;
    private BigDecimal price;
    private String introduction;
    private Blob photo;
    private String countries;
    private Long salesVolume;
    private Long number;
}
