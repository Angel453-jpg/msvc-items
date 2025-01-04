package com.angel.springcloud.msvc.items.services;

import com.angel.springcloud.msvc.items.models.Item;
import com.angel.springcloud.msvc.items.models.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

//@Primary
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder client;

    public ItemServiceWebClient(WebClient.Builder builder) {
        this.client = builder;
    }

    @Override
    public List<Item> findAll() {

        return this.client.build()
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
        return Optional.ofNullable(this.client.build()
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

        return client.build()
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

        return client.build()
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

        client.build()
                .delete()
                .uri("/{id}", params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }
}
