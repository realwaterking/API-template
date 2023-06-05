package com.chzu.apitemplate.controller;

import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.common.Result;
import com.chzu.apitemplate.model.entity.Product;
import com.chzu.apitemplate.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author water king
 * @time 2023/6/5
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource
    ProductService productService;

    @GetMapping("/getproductlist")
    public BaseResponse<List<Product>> getProductList() {
        List<Product> productList = productService.getProductAll();
        return Result.success(productList);
    }

    @GetMapping("/getproductbyid/{id}")
    public BaseResponse<Product> getProductByID(@PathVariable String id) {
        Product productById = productService.getProductById(Long.getLong(id));
        return Result.success(productById);
    }

    @DeleteMapping("/deletebyid/{id}")
    public BaseResponse<Boolean> deleteByID(@PathVariable String id) {
        BaseResponse<Boolean> booleanBaseResponse = productService.deleteById(Long.getLong(id));
        return booleanBaseResponse;
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody Product product) {
        BaseResponse<Boolean> booleanBaseResponse = productService.updateProduct(product);
        return booleanBaseResponse;
    }

    @PostMapping("/save")
    public BaseResponse<Boolean> save(@RequestBody Product product) {
        boolean save = productService.save(product);
        return Result.success(save);
    }


}
