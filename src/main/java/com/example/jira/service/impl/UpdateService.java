package com.example.jira.service.impl;

import com.example.jira.domain.Sprint;
import com.example.jira.repository.SprintRepository;
import com.example.jira.service.BoardService;
import com.example.jira.service.CategoryService;
import com.example.jira.service.ProjectService;
import com.example.jira.service.SprintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UpdateService {
    private final CategoryService categoryService;
    private final ProjectService projectsService;
    private final BoardService boardService;
    private final SprintService sprintService;

    UpdateService(CategoryService categoryService, ProjectService projectsService, BoardService boardService, SprintService sprintService) {
        this.categoryService = categoryService;
        this.projectsService = projectsService;
        this.boardService = boardService;
        this.sprintService = sprintService;
    }
@Autowired
    SprintRepository spp;
    public Mono<Boolean> updateDB() {
        System.out.println("ok");
        return categoryService
                .getAllCategories()
                .flatMap(categoryService::save)
                .then(Mono.just("true"))
                .map(val -> projectsService
                        .allProjects()
                        .flatMap(projectsService::save)
                        // NOW POPULATING THE BD
                        .flatMap(project -> boardService
                                .allBoardByProjects(project.getId())
                                .flatMap(board -> boardService.save(board.toBuilder().project(project).build()))
                                .flatMap(board -> sprintService.getAllSprint(board.getId())
                                        .map(sprint -> sprintService.getReport(sprint.getOriginBoardId(), sprint.getId())
                                                .map(report -> {
                                                    Sprint theSprint = sprint.setReport(report);
                                                    theSprint.kpi();
                                                    theSprint.dureSpr();
                                                    theSprint.liste(spp,board.getId());
                                                    theSprint.calculAccele(spp,board.getId());

                                                    sprintService.save(theSprint.toBuilder().board(board).build());
                                                    return Mono.just(true);
                                                }).subscribe()
                                        )
                                )
                        )
                        .then(Mono.just("true"))
                        .subscribe(
                                success -> {
                                    log.info("GOT  PROJECTS");
                                },
                                error -> {
                                    error.printStackTrace();
                                    log.info("PROJECTS ERROR "+error.getMessage());
                                })
                ).then(Mono.just(true));
    }
}
