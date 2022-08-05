package com.example.jira.service.impl;


import com.example.jira.config.ApplicationProperties;
import com.example.jira.domain.Project;
import com.example.jira.repository.ProjectRepository;
import com.example.jira.service.ProjectService;
import com.example.jira.service.dto.jiraApi.ProjectDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;

    public ProjectServiceImpl(ProjectRepository projectRepository, ApplicationProperties applicationProperties) {
        this.projectRepository = projectRepository;
        this.applicationProperties = applicationProperties;
        this.webClient = WebClient.builder()
                .baseUrl(this.applicationProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.set(HttpHeaders.AUTHORIZATION, this.applicationProperties.getToken()))
                .build();
    }

    @Override
    public Flux<Project> allProjects() {
        return webClient.get()
                .uri("/rest/api/2/project")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(ProjectDto.class))
                .flatMap(
                        projectDto -> {
                            Project aProject = new Project() ;
                            BeanUtils.copyProperties(projectDto, aProject);
                            return Mono.just(aProject);
                        }
                );
    }

    @Override
    public Mono<Project> save(Project project) {
        projectRepository.saveAndFlush(project);
        return Mono.just(project);
    }
}
