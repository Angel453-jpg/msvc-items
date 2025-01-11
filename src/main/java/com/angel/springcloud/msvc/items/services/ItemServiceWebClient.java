package com.angel.springcloud.msvc.items.services;

import com.angel.libs.msvc.commons.entities.Product;
import com.angel.springcloud.msvc.items.models.Item;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

//@Primary
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient client;

    public ItemServiceWebClient(WebClient builder) {
        this.client = builder;
    }

    @Override
    public List<Item> findAll() {

        return this.client
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList()
                .block();

    }

    @Override
    public Optional<Item> findById(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

//        try {
        return Optional.ofNullable(this.client
                .get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .block());
//        } catch (WebClientResponseException e) {
//            return Optional.empty();
//        }

    }

    @Override
    public Product save(Product product) {

        return client
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();

    }

    @Override
    public Product update(Product product, Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return client
                .put()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();

    }

    @Override
    public void delete(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        client
                .delete()
                .uri("/{id}", params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }
}
