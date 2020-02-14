package com.example.jira.service;

import com.example.jira.domain.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAllCategories();

    Mono<Category> save(Category category);
}
