package com.example.jira.service;

import com.example.jira.api.report.Report;
import com.example.jira.domain.Sprint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SprintService {

    Mono<Report> getReport(int originBoardId, int id);
    Flux<Sprint> getAllSprint(int id) ;
    Mono<Sprint> save(Sprint sprint);
}
