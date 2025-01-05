package com.angel.springcloud.msvc.items.clients;

import com.angel.libs.msvc.commons.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product details(@PathVariable Long id);

    @PostMapping
    Product create(@RequestBody Product product);

    @PutMapping("/{id}")
    Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
