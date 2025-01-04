package com.angel.springcloud.msvc.items.services;

import com.angel.springcloud.msvc.items.models.Item;
import com.angel.springcloud.msvc.items.models.Product;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);

}
