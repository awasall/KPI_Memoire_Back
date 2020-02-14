package com.example.jira.service;

import com.example.jira.domain.Project;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {

    Flux<Project> allProjects();

    Mono<Project> save(Project project);
}
