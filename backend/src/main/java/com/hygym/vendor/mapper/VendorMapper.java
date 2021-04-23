package com.hygym.vendor.mapper;

import com.hygym.vendor.entity.Area;
import com.hygym.vendor.entity.AreaDetail;
import com.hygym.vendor.entity.OrderDetail;
import com.hygym.vendor.entity.OrderFilter;
import com.hygym.vendor.entity.ProductDetail;
import com.hygym.vendor.entity.ProductFilter;
import com.hygym.vendor.entity.ShopDetail;

import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 9:37 下午
 */
public interface VendorMapper {

    List<Area> selectExample();

    List<ShopDetail> selectShopDetailById(List<Long> shopId);

    List<ProductDetail> selectProductDetailById(List<Long> productId);

    List<OrderDetail> selectOrderDetailById(List<Long> orderId);

    List<AreaDetail> selectAreaDetailById(List<Long> areaId);

    int updateProductDetail(ProductDetail detail);

    int deleteProductDetail(Long productId);

    int updateShopDetail(ShopDetail detail);

    int insertProductDetail(ProductDetail detail);

    List<ProductDetail> selectProductDetailByFilter(ProductFilter filter);

    int insertOrderDetail(OrderDetail detail);

    List<OrderDetail> selectOrderDetailByFilter(OrderFilter filter);

    int insertAreaDetail(AreaDetail detail);

    int updateAreaDetail(AreaDetail detail);

    int deleteAreaDetail(Long areaId);
}
