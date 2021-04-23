package com.hygym.vendor.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Yiming Gong
 * @date 2021/4/19 4:05 下午
 */
@Data
public class ShopSumVO {

    /**
     * 店铺总销售额
     */
    private BigDecimal money;

    /**
     * 所有商品总销量
     */
    private Long amount;

    /**
     * 总顾客数量（不重复）
     */
    private Long customerNum;

    /**
     * 总销售国家数量
     */
    private Integer areaNum;
}
