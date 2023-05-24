package com.chzu.apitemplate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chzu.apitemplate.mapper.OrderMapper;
import com.chzu.apitemplate.model.entity.Order;
import com.chzu.apitemplate.service.OrderService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    @Cacheable(value = "orders", key = "#id")
    public Order getOrderById(Long id) {
        return orderMapper.selectOneById(id);
    }

    @Override
    @CachePut(value = "orders", key = "#result.id")
    public void saveOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderMapper.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }
}
