package com.hygym.vendor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 9:16 下午
 */
@Data
@AllArgsConstructor
public class AreaPackage {

    private Long areaGroupId;

    private String name;

    private String desc;

    private List<String> area;
}
