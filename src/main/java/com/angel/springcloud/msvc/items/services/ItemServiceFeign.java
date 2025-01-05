package com.angel.springcloud.msvc.items.services;

import com.angel.libs.msvc.commons.entities.Product;
import com.angel.springcloud.msvc.items.clients.ProductFeignClient;
import com.angel.springcloud.msvc.items.models.Item;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ItemServiceFeign implements ItemService {

    private final ProductFeignClient productFeignClient;

    public ItemServiceFeign(ProductFeignClient client) {
        this.productFeignClient = client;
    }

    @Override
    public List<Item> findAll() {
        return productFeignClient.findAll()
                .stream()
                .map(product ->
                        new Item(product, new Random().nextInt(10) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = productFeignClient.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            return Optional.empty();
        }
    }

    @Override
    public Product save(Product product) {
        return productFeignClient.create(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productFeignClient.update(product, id);
    }

    @Override
    public void delete(Long id) {
        productFeignClient.delete(id);
    }
}
