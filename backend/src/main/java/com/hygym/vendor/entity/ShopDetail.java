package com.hygym.vendor.entity;

import lombok.Data;

import java.sql.Blob;

/**
 * @author Yiming Gong
 * @date 2021/4/20 8:14 下午
 */
@Data
public class ShopDetail {
    private Long shopId;
    private String name;
    private Blob photo;
    private String productIds;
    private String areaGroupIds;
    private String orderIds;
}
