package com.example.jira.service.impl;

import com.example.jira.config.ApplicationProperties;
import com.example.jira.domain.Category;
import com.example.jira.repository.CategoryRepository;
import com.example.jira.service.CategoryService;
import com.example.jira.service.dto.jiraApi.CategoryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ApplicationProperties applicationProperties) {
        this.categoryRepository = categoryRepository;
        this.applicationProperties = applicationProperties;
        this.webClient = WebClient.builder()
                .baseUrl(this.applicationProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.set(HttpHeaders.AUTHORIZATION, this.applicationProperties.getToken()))
                .build();
    }

    @Override
    public Flux<Category> getAllCategories() {
        return webClient.get()
                .uri("/rest/api/2/projectCategory")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(CategoryDto.class))
                .flatMap(categoryDto -> {
                    Category aCategory = new Category() ;
                    BeanUtils.copyProperties(categoryDto, aCategory);
                    return Mono.just(aCategory);
                });
    }

    @Override
    public Mono<Category> save(Category category) {
        categoryRepository.saveAndFlush(category);
        return Mono.just(category);
    }

}
