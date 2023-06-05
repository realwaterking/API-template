package com.chzu.apitemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.model.entity.Product;

import java.util.List;

public interface ProductService extends IService<Product> {

    List<Product> getProductAll();

    Product getProductById(Long id);

    boolean save(Product product);

    BaseResponse<Boolean> deleteById(Long id);

    BaseResponse<Boolean> updateProduct(Product product);
}
