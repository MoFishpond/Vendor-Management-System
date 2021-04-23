package com.hygym.vendor.mapper;

import com.hygym.vendor.entity.Area;
import com.hygym.vendor.entity.AreaDetail;
import com.hygym.vendor.entity.OrderDetail;
import com.hygym.vendor.entity.OrderFilter;
import com.hygym.vendor.entity.ProductDetail;
import com.hygym.vendor.entity.ProductFilter;
import com.hygym.vendor.entity.ShopDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yiming Gong
 * @date 2021/4/19 9:37 下午
 */
public interface VendorMapper {

    List<Area> selectExample();

    List<ShopDetail> selectShopDetailById(@Param("shopId") List<Long> shopId);

    List<ProductDetail> selectProductDetailById(@Param("productId") List<Long> productId);

    List<OrderDetail> selectOrderDetailById(@Param("orderId") List<Long> orderId);

    List<AreaDetail> selectAreaDetailById(@Param("areaId") List<Long> areaId);

    int updateProductDetail(@Param("detail") ProductDetail detail);

    int deleteProductDetail(@Param("productId") Long productId);

    int updateShopDetail(@Param("detail") ShopDetail detail);

    int insertProductDetail(@Param("detail")ProductDetail detail);

    List<ProductDetail> selectProductDetailByFilter(@Param("filter")ProductFilter filter);

    int insertOrderDetail(@Param("detail")OrderDetail detail);

    List<OrderDetail> selectOrderDetailByFilter(@Param("filter")OrderFilter filter);

    int insertAreaDetail(@Param("detail")AreaDetail detail);

    int updateAreaDetail(@Param("detail")AreaDetail detail);

    int deleteAreaDetail(@Param("areaId")Long areaId);
}
