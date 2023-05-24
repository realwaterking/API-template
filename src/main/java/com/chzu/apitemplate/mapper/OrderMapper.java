package com.chzu.apitemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chzu.apitemplate.model.entity.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<Order> selectAll();

    Order selectOneById(Long id);
}
