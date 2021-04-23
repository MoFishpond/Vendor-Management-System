package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yiming Gong
 * @date 2021/4/22 10:33 下午
 */
@Data
@AllArgsConstructor
public class AreaDetail {
    private Long areaId;
    private String name;
    private String description;
    private String countries;
}
