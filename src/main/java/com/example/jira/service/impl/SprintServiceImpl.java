package com.example.jira.service.impl;

import com.example.jira.api.report.Report;
import com.example.jira.config.ApplicationProperties;
import com.example.jira.domain.Sprint;
import com.example.jira.repository.SprintRepository;
import com.example.jira.service.SprintService;
import com.example.jira.service.dto.jiraApi.SprintDto;
import com.example.jira.web.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;

    public SprintServiceImpl(com.example.jira.repository.SprintRepository sprintRepository, ApplicationProperties applicationProperties) {
        this.sprintRepository = sprintRepository;
        this.applicationProperties = applicationProperties;
        this.webClient = WebClient.builder()
                .baseUrl(this.applicationProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.set(HttpHeaders.AUTHORIZATION, this.applicationProperties.getToken()))
                .build();
    }

    @Override
    public Mono<Report> getReport(int originBoardId, int id) {
        final String url = "/rest/greenhopper/1.0/rapid/charts/sprintreport?rapidViewId=" + originBoardId + "&sprintId=" + id;
        Mono<Report> report = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                           clientResponse ->
                                   Mono.empty())
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())


                .bodyToMono(Report.class)
                .doOnError(e -> {
                    log.info("ERROR URL -- {}", this.applicationProperties.getBaseUrl() + url);
                    Mono.empty();
                });
        //.doOnError(e ->e.printStackTrace());
        return report.filter(report1 -> !report1.equals(Mono.empty()));
    }

    public Flux<Sprint> getAllSprint(int id) {
        return webClient.get()
                .uri("rest/agile/1.0/board/" + id + "/sprint?")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToMono(SprintDto.class))
                .map(SprintDto::getValues)
                .map(sprintDtos -> Flux.fromIterable(
                        sprintDtos
                        .stream()
                        .map(sprintDto -> {
                            Sprint aSprint = new Sprint();
                            BeanUtils.copyProperties(sprintDto, aSprint);
                            return aSprint;
                        }).collect(Collectors.toList())
                        )
                )
                .flatMap(sprintFlux -> sprintFlux)
        ;

    }

    /*
    public List<IssueType>  issueTypes (){
        List<IssueType> issueTypes = new ArrayList<>();
        WebClient client = (new Client()).getClient() ;
        Flux< IssueType> response = client.get()
                .uri("/rest/api/2/issuetype")
                .retrieve()
                .bodyToFlux(IssueType.class);
        response.subscribe(project -> issueTypes.add(project));
        return  issueTypes;
    }
 */

    @Override
    public Mono<Sprint> save(Sprint sprint) {
        sprintRepository.saveAndFlush(sprint);
        return Mono.just(sprint);
    }


}


