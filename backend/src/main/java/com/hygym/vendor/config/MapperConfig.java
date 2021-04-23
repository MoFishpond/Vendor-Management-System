package com.hygym.vendor.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yiming Gong
 * @date 2021/4/19 9:34 下午
 */
@Configuration
@MapperScan(basePackages = "com.hygym.vendor.mapper")
public class MapperConfig {
}
