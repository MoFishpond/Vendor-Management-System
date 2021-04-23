package com.hygym.vendor.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hygym.vendor.entity.AreaDetail;
import com.hygym.vendor.entity.OrderDetail;
import com.hygym.vendor.entity.ProductDetail;
import com.hygym.vendor.entity.ProductFilter;
import com.hygym.vendor.entity.ShopDetail;
import com.hygym.vendor.mapper.VendorMapper;
import com.hygym.vendor.vo.Product;
import com.hygym.vendor.vo.ProductArray;
import com.hygym.vendor.vo.ProductDTO;
import com.hygym.vendor.vo.ShopSumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yiming Gong
 * @date 2021/4/19 4:01 下午
 */
@RestController
public class MainController {

    private static long p = 15;

    @Autowired
    private VendorMapper vendorMapper;

    @GetMapping("/rest/i/vms/shop/getShopInfo")
    public ShopSumVO getShopInfo(@RequestParam("shopId") Long shopId) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<AreaDetail> areaDetails = vendorMapper.selectAreaDetailById(Arrays.stream(shopDetail
                .getAreaGroupIds().split(",")).map(Long::parseUnsignedLong).collect(Collectors.toList()));
        List<ProductDetail> productDetails = vendorMapper.selectProductDetailById(Arrays.stream(shopDetail
                .getProductIds().split(",")).map(Long::parseUnsignedLong).collect(Collectors.toList()));
        List<OrderDetail> orderDetails = vendorMapper.selectOrderDetailById(Arrays.stream(shopDetail
                .getOrderIds().split(",")).map(Long::parseUnsignedLong).collect(Collectors.toList()));
        ShopSumVO shopSumVO = new ShopSumVO();
        shopSumVO.setAmount(productDetails.stream().map(ProductDetail::getSalesVolume).reduce(Long::sum).orElse(0L));
        shopSumVO.setAreaNum(areaDetails.size());
        shopSumVO.setCustomerNum(orderDetails.stream().map(OrderDetail::getCustomerId).distinct().count());
        shopSumVO.setMoney(orderDetails.stream().map(OrderDetail::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        return shopSumVO;
    }

    @GetMapping("/rest/i/vms/product/getAll")
    public ProductArray<Product> getAll(@RequestParam("shopId") Long shopId) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<ProductDetail> productDetails = vendorMapper.selectProductDetailById(Arrays.stream(shopDetail
                .getProductIds().split(",")).map(Long::parseUnsignedLong).collect(Collectors.toList()));
        List<Product> products = productDetails.stream().map(s -> new Product(s.getProductId(), s.getName()
                , s.getPrice(), s.getIntroduction(), s.getPhoto()
                , Arrays.asList(s.getCountries().split(",")), s.getNumber())).collect(Collectors.toList());
//        System.out.println(shopId);
//        Product product1 = Product.builder()
//                .productId(11L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(11L).build();
//        Product product2 = Product.builder()
//                .productId(12L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(12L).build();
        ProductArray<Product> productArray = new ProductArray<>();
        productArray.setData(products);
        return productArray;
    }
//根据条件，获取某商品销售情况  待补充
//    @GetMapping("/rest/i/vms/product/getSalesInfo")
//    @ResponseBody
    @PostMapping("/rest/i/vms/product/edit")
    public ProductArray<Product> editProduct(@RequestBody ProductDTO dto) {
        Product product = new Product(dto.getProductId(), dto.getName(), dto.getPrice(), dto.getIntroduction(), dto.getImage(), dto.getArea(), dto.getStock());
        Long shopId = dto.getShopId();
        ProductDetail productDetail = new ProductDetail(product.getProductId(), product.getName()
                        , product.getPrice(), product.getIntroduction(), null, Joiner.on(",").join(product.getArea()), null, product.getStock());
        vendorMapper.updateProductDetail(productDetail);
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<ProductDetail> productDetails = vendorMapper.selectProductDetailById(Arrays.stream(shopDetail.getProductIds()
                .split(",")).map(Long::parseUnsignedLong).collect(Collectors.toList()));
        List<Product> products = productDetails.stream().map(s -> new Product(s.getProductId(), s.getName(), s.getPrice()
                , s.getIntroduction(), s.getPhoto(), Arrays.asList(s.getCountries().split(",")), s.getNumber())).collect(Collectors.toList());

//        System.out.println(product);
//        Product product2 = Product.builder()
//                .productId(12L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(12L).build();
        ProductArray<Product> productArray = new ProductArray<>();
        productArray.setData(products);
        return productArray;
    }

    @PostMapping("/rest/i/vms/product/delete")
    public ProductArray<Product> deleteProduct(@RequestBody ProductDTO dto) {
        Long shopId = dto.getShopId();
        Long productId = dto.getProductId();
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<String> oldPr = new ArrayList<>(Arrays.asList(shopDetail.getProductIds().split(",")));
        oldPr.remove(productId.toString());
        shopDetail.setProductIds(Joiner.on(",").join(oldPr));
        vendorMapper.deleteProductDetail(productId);
        vendorMapper.updateShopDetail(shopDetail);
        List<ProductDetail> productDetails = vendorMapper
                .selectProductDetailById(oldPr.stream().map(Long::parseUnsignedLong).collect(Collectors.toList()));
        List<Product> products = productDetails.stream().map(s -> new Product(s.getProductId(), s.getName(), s.getPrice()
                , s.getIntroduction(), s.getPhoto(), Arrays.asList(s.getCountries().split(",")), s.getNumber())).collect(Collectors.toList());

//        System.out.println(shopId);
//        System.out.println(productId);
//        Product product2 = Product.builder()
//                .productId(12L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(12L).build();
        ProductArray<Product> productArray = new ProductArray<>();
        productArray.setData(products);
        return productArray;
    }

    @PostMapping("/rest/i/vms/product/add")
    public ProductArray<Product> addProduct(@RequestBody ProductDTO dto) {
        Product product = new Product(p++, dto.getName(), dto.getPrice(), dto.getIntroduction(), dto.getImage(), dto.getArea(), dto.getStock());
        Long shopId = dto.getShopId();
        ProductDetail detail = new ProductDetail(product.getProductId(), product.getName(), product.getPrice(), product.getIntroduction(),
                null, Joiner.on(",").join(product.getArea()), 0L, product.getStock());
        vendorMapper.insertProductDetail(detail);
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<String> oldPr = new ArrayList<>(Arrays.asList(shopDetail.getProductIds().split(",")));
        List<ProductDetail> productDetails = vendorMapper
                .selectProductDetailById(oldPr.stream().map(Long::parseUnsignedLong).collect(Collectors.toList()));
        productDetails.add(detail);
        oldPr.add(detail.getProductId().toString());
        shopDetail.setProductIds(Joiner.on(",").join(oldPr));
        vendorMapper.updateShopDetail(shopDetail);
        List<Product> products = productDetails.stream().map(s -> new Product(s.getProductId(), s.getName(), s.getPrice()
                , s.getIntroduction(), s.getPhoto(), Arrays.asList(s.getCountries().split(",")), s.getNumber())).collect(Collectors.toList());
//        System.out.println(product);
//        Product product2 = Product.builder()
//                .productId(12L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(12L).build();
        ProductArray<Product> productArray = new ProductArray<>();
        productArray.setData(products);
        return productArray;
    }

    @GetMapping("/rest/i/vms/product/get")
    public ProductArray<Product> getProductByFilter(@RequestBody ProductFilter filter) {
        List<ProductDetail> productDetails = vendorMapper.selectProductDetailByFilter(filter);
        List<Product> products = productDetails.stream().map(s -> new Product(s.getProductId(), s.getName(), s.getPrice()
                , s.getIntroduction(), s.getPhoto(), Arrays.asList(s.getCountries().split(",")), s.getNumber())).collect(Collectors.toList());
//        System.out.println(filter);
//        Product product2 = Product.builder()
//                .productId(12L)
//                .name("pp")
//                .price(new BigDecimal("1.00"))
//                .introduction("pph")
//                .area(Lists.newArrayList("US","China"))
//                .stock(12L).build();
        ProductArray<Product> productArray = new ProductArray<>();
        productArray.setData(products);
        return productArray;
    }


}
