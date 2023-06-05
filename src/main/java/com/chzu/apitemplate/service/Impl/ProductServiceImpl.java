package com.chzu.apitemplate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.common.ErrorCode;
import com.chzu.apitemplate.common.Result;
import com.chzu.apitemplate.exception.BusinessException;
import com.chzu.apitemplate.mapper.ProductMapper;
import com.chzu.apitemplate.model.entity.Product;
import com.chzu.apitemplate.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductAll() {
        return productMapper.selectAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productMapper.selectOneById(id);
    }

    @Override
    public boolean save(Product product) {

        if (productMapper.insert(product) >= 0) {
            return true;
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

    }


    @Override
    public BaseResponse<Boolean> deleteById(Long id) {
        productMapper.deleteById(id);
        return Result.success(true);
    }

    @Override
    public BaseResponse<Boolean> updateProduct(Product product) {
         productMapper.updateProduct(product);
         return Result.success(true);
    }
}
