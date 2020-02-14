package com.example.jira.service.dto.jiraApi;

import com.example.jira.domain.Board;
import com.example.jira.domain.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProjectDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name ;

    private Set<Board> boards =  new HashSet<>();

    private Category projectCategory;
}
