package com.example.jira.service.dto.jiraApi;

import com.example.jira.domain.Project;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String description;

    private String name;

}
