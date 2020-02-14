package com.example.jira.service.dto.jiraApi;

import com.example.jira.domain.Project;
import com.example.jira.domain.Sprint;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BoardDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String type;
    private Project project;
}
