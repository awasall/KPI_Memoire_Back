package com.example.jira.service;

import com.example.jira.domain.Board;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardService {

    Flux<Board> allBoardByProjects(String id);

    Mono<Board> save(Board board);
}
