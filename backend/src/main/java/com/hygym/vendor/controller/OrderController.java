package com.hygym.vendor.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hygym.vendor.entity.Area;
import com.hygym.vendor.entity.AreaDetail;
import com.hygym.vendor.entity.AreaPackage;
import com.hygym.vendor.entity.OrderDetail;
import com.hygym.vendor.entity.OrderFilter;
import com.hygym.vendor.entity.ShopDetail;
import com.hygym.vendor.mapper.VendorMapper;
import com.hygym.vendor.vo.Detail;
import com.hygym.vendor.vo.Order;
import com.hygym.vendor.vo.OrderDTO;
import com.hygym.vendor.vo.ProductArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yiming Gong
 * @date 2021/4/19 5:51 下午
 */
@RestController
public class OrderController {

    @Autowired
    private VendorMapper vendorMapper;

    private static long o = 10L;

    @GetMapping("/rest/i/vms/order/getALL")
    public ProductArray<Order> getAllOrder(@RequestParam("shopId") Long shopId) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<Long> orderIds = Arrays.stream(shopDetail.getOrderIds().split(","))
                .map(Long::parseUnsignedLong).collect(Collectors.toList());
        List<OrderDetail> orderDetails = vendorMapper.selectOrderDetailById(orderIds);
        List<Order> orders = orderDetails.stream().map(s -> {
            String[] split = s.getProducts().split(";");
            List<Detail> inner = Arrays.stream(split).map(m -> {
                String[] split1 = m.split(":");
                return new Detail(Long.parseUnsignedLong(split1[0]), Long.parseUnsignedLong(split1[1]));
            }).collect(Collectors.toList());
            return new Order(s.getOrderId(), s.getCustomerId(), s.getCountry(),
                    inner, s.getAmount());
        }).collect(Collectors.toList());
//        System.out.println(shopId);
        ProductArray<Order> orderProductArray = new ProductArray<>();
        orderProductArray.setData(orders);
        return orderProductArray;
    }

    @PostMapping("/rest/i/vms/order/add")
    public ProductArray<Order> addOrder(@RequestBody OrderDTO dto) {
        Order order = new Order(o++, dto.getCustomerId(), dto.getArea(), dto.getDetails(), dto.getMoney());
        Long shopId = dto.getShopId();
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<Long> orderIds = Arrays.stream(shopDetail.getOrderIds().split(","))
                .map(Long::parseUnsignedLong).collect(Collectors.toList());
        List<OrderDetail> orderDetails = vendorMapper.selectOrderDetailById(orderIds);
        StringBuffer buffer = new StringBuffer();
        order.getDetails().forEach(s -> {
            buffer.append(s.getProductId()).append(":").append(s.getAmount()).append(";");
        });
        buffer.deleteCharAt(buffer.length()-1);
        OrderDetail orderDetail = new OrderDetail(order.getOrderId(), new Date(), order.getCustomerId(), order.getArea(),
                buffer.toString(), order.getMoney());
        vendorMapper.insertOrderDetail(orderDetail);
        orderIds.add(orderDetail.getOrderId());
        shopDetail.setOrderIds(Joiner.on(",").join(orderIds));
        vendorMapper.updateShopDetail(shopDetail);
        orderDetails.add(orderDetail);
        List<Order> orders = orderDetails.stream().map(s -> {
            String[] split = s.getProducts().split(";");
            List<Detail> inner = Arrays.stream(split).map(m -> {
                String[] split1 = m.split(":");
                return new Detail(Long.parseUnsignedLong(split1[0]), Long.parseUnsignedLong(split1[1]));
            }).collect(Collectors.toList());
            return new Order(s.getOrderId(), s.getCustomerId(), s.getCountry(),
                    inner, s.getAmount());
        }).collect(Collectors.toList());
//        System.out.println(order);
        ProductArray<Order> orderProductArray = new ProductArray<>();
        orderProductArray.setData(orders);
        return orderProductArray;
    }

    @GetMapping("/rest/i/vms/order/get")
    public ProductArray<Order> getOrderByFilter(OrderFilter orderFilter) {
//        System.out.println(orderFilter);
        List<OrderDetail> orderDetails = vendorMapper.selectOrderDetailByFilter(orderFilter);
        List<Order> orders = orderDetails.stream().map(s -> {
            String[] split = s.getProducts().split(";");
            List<Detail> inner = Arrays.stream(split).map(m -> {
                String[] split1 = m.split(":");
                return new Detail(Long.parseUnsignedLong(split1[0]), Long.parseUnsignedLong(split1[1]));
            }).collect(Collectors.toList());
            return new Order(s.getOrderId(), s.getCustomerId(), s.getCountry(),
                    inner, s.getAmount());
        }).collect(Collectors.toList());
        ProductArray<Order> orderProductArray = new ProductArray<>();
        orderProductArray.setData(orders);
        return orderProductArray;
    }

    @GetMapping("/rest/i/vms/area/getALL")
    public ProductArray<AreaPackage> getAreaPackage(@RequestParam("shopId") Long shopId) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(shopId)).get(0);
        List<Long> areas = Arrays.asList(shopDetail.getAreaGroupIds().split(","))
                .stream().map(Long::parseUnsignedLong).collect(Collectors.toList());
        List<AreaDetail> areaDetails = vendorMapper.selectAreaDetailById(areas);
        List<AreaPackage> areaPackages = areaDetails.stream().map(s -> {
            List<String> area = Arrays.asList(s.getCountries().split(","));
            return new AreaPackage(s.getAreaId(), s.getName(), s.getDescription(), area);
        }).collect(Collectors.toList());
//        System.out.println(shopId);
        ProductArray<AreaPackage> areaPackageProductArray = new ProductArray<>();
        areaPackageProductArray.setData(areaPackages);
        return areaPackageProductArray;
    }

    @PostMapping("/rest/i/vms/area/add")
    public ProductArray<AreaPackage> addArea(@RequestBody Area area) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(area.getShopId())).get(0);
        List<Long> areas = Arrays.asList(shopDetail.getAreaGroupIds().split(","))
                .stream().map(Long::parseUnsignedLong).collect(Collectors.toList());
        List<AreaDetail> areaDetails = vendorMapper.selectAreaDetailById(areas);
        List<AreaPackage> areaPackages = areaDetails.stream().map(s -> {
            List<String> list = Arrays.asList(s.getCountries().split(","));
            return new AreaPackage(s.getAreaId(), s.getName(), s.getDescription(), list);
        }).collect(Collectors.toList());
        AreaPackage areaPackage = new AreaPackage(area.getAreaGroupId(),area.getName(),area.getDesc(),area.getArea());
        AreaDetail areaDetail = new AreaDetail(area.getAreaGroupId(), area.getName(), area.getDesc(), Joiner.on(",").join(area.getArea()));
        vendorMapper.insertAreaDetail(areaDetail);
        areas.add(areaDetail.getAreaId());
        shopDetail.setAreaGroupIds(Joiner.on(",").join(areas));
        vendorMapper.updateShopDetail(shopDetail);
        areaPackages.add(areaPackage);
//        System.out.println(area);
        ProductArray<AreaPackage> areaPackageProductArray = new ProductArray<>();
        areaPackageProductArray.setData(areaPackages);
        return areaPackageProductArray;
    }

    @PostMapping("/rest/i/vms/area/edit")
    public ProductArray<AreaPackage> editArea(@RequestBody Area area) {
        AreaDetail areaDetail = new AreaDetail(area.getAreaGroupId(), area.getName(), area.getDesc(), Joiner.on(",").join(area.getArea()));
        vendorMapper.updateAreaDetail(areaDetail);
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(area.getShopId())).get(0);
        List<Long> areas = Arrays.asList(shopDetail.getAreaGroupIds().split(","))
                .stream().map(Long::parseUnsignedLong).collect(Collectors.toList());
        List<AreaDetail> areaDetails = vendorMapper.selectAreaDetailById(areas);
        List<AreaPackage> areaPackages = areaDetails.stream().map(s -> {
            List<String> list = Arrays.asList(s.getCountries().split(","));
            return new AreaPackage(s.getAreaId(), s.getName(), s.getDescription(), list);
        }).collect(Collectors.toList());
//        System.out.println(area);
        ProductArray<AreaPackage> areaPackageProductArray = new ProductArray<>();
        areaPackageProductArray.setData(areaPackages);
        return areaPackageProductArray;
    }

    @PostMapping("/rest/i/vms/area/delete")
    public ProductArray<AreaPackage> deleteArea(@RequestBody Area area) {
        ShopDetail shopDetail = vendorMapper.selectShopDetailById(Lists.newArrayList(area.getShopId())).get(0);
        List<Long> areas = Arrays.asList(shopDetail.getAreaGroupIds().split(","))
                .stream().map(Long::parseUnsignedLong).collect(Collectors.toList());
        areas.remove(area.getAreaGroupId());
        shopDetail.setAreaGroupIds(Joiner.on(",").join(areas));
        vendorMapper.updateShopDetail(shopDetail);
        vendorMapper.deleteAreaDetail(area.getAreaGroupId());
        List<AreaDetail> areaDetails = vendorMapper.selectAreaDetailById(areas);
        List<AreaPackage> areaPackages = areaDetails.stream().map(s -> {
            List<String> list = Arrays.asList(s.getCountries().split(","));
            return new AreaPackage(s.getAreaId(), s.getName(), s.getDescription(), list);
        }).collect(Collectors.toList());
//        System.out.println(area);
        ProductArray<AreaPackage> areaPackageProductArray = new ProductArray<>();
        areaPackageProductArray.setData(areaPackages);
        return areaPackageProductArray;
    }
}
