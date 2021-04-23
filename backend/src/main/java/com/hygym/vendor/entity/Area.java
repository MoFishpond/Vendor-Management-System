package com.hygym.vendor.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 9:21 下午
 */
@Data
public class Area {

    private Long shopId;

    private Long areaGroupId;

    private String name;

    private String desc;

    private List<String> area;
}
