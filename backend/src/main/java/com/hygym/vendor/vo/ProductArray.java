package com.hygym.vendor.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 5:13 下午
 */
@Data
public class ProductArray <T> {
    private List<T> data;
}
