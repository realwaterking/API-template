package com.chzu.apitemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chzu.apitemplate.model.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    Order getOrderById(Long id);

    void saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrderById(Long id);

    List<Order> findAll();
}
