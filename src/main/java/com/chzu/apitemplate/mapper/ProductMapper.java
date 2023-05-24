package com.chzu.apitemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chzu.apitemplate.model.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    Product updateProduct(Product product);

    @Select("select * from product")
    List<Product> selectAll();

    @Select("select * from product where id = #{id}")
    Product selectOneById(Long id);

}
