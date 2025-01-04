package com.angel.springcloud.msvc.items.clients;

import com.angel.springcloud.msvc.items.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product details(@PathVariable Long id);

}
