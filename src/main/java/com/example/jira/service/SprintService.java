package com.example.jira.service;

import com.example.jira.api.report.Report;
import com.example.jira.domain.Composant;
import com.example.jira.domain.Sprint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface SprintService {
    Mono<Report> getReport(int originBoardId, int id);
    Mono<Composant> getMeasures(String projectKey, String metric, Date from, int pageIndex, int pageSize);
    Flux<Sprint> getAllSprint(int id) ;
    Mono<Sprint> save(Sprint sprint);
}
