package com.hygym.vendor.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/23 11:40 下午
 */
@Data
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    private String name;

    private BigDecimal price;

    private String introduction;

    private Blob image;

    private List<String> area;

    private Long stock;

    private Long shopId;
}
