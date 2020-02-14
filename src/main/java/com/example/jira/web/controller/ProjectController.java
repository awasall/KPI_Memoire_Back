package com.example.jira.web.controller;

import com.example.jira.domain.Board;
import com.example.jira.domain.Project;
import com.example.jira.domain.Sprint;
import com.example.jira.repository.ProjectRepository;
import com.example.jira.web.exceptions.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Api(description = "API pour les opérations CRUD sur les Projets.")
@RestController
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @ApiOperation(value = "This get all the projects in the databases")
    @GetMapping(value = "/project")
    public List<Project> getProjects() {
        return projectRepository.findAllByOrderByIdAsc();
    }

   @ApiOperation(value = "This get all the projects in the databases by name provided")
    @GetMapping(value = "/project/search/{name}")
    public List<Project> getProjectsByName(@PathVariable String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    @ApiOperation(value = "This get all project of a specific category by project_id")
    @GetMapping(value = "/project/category/{id}")
    public Page<Project> getProjectsCategory(@PathVariable Long id , @PageableDefault(page = 0, size = 5) @SortDefault.SortDefaults({
            @SortDefault(sort = "id", direction = Sort.Direction.ASC),
    })  Pageable pageable) {
        return  projectRepository.findByProjectCategoryId(id,pageable);
    }
/*
    @GetMapping(value = "/project/category/{id}/page/{pageNumber}")
    public Page<Project> getProjectsCategory(@PathVariable Long id , @PathVariable int  pageNumber) {
        Pageable pages = PageRequest.of(pageNumber, 2);
        Page<Project> projects = projectRepository.findByProjectCategoryId(id,pages);
        //if (projects == null || !projects.hasContent()) throw new ResourceNotFoundException("Categorie", id);
        return projects;
    }*/

    @ApiOperation(value = "This get the projects not categorized")
    @GetMapping(value = "/project/category/autres")
    public Page<Project> getProjectsUnCategorized( @PageableDefault(page = 0, size = 5)  @SortDefault.SortDefaults({
            @SortDefault(sort = "id", direction = Sort.Direction.ASC),
    }) Pageable pageable) {
        return projectRepository.findByProjectCategoryIdIsNull(pageable);
    }


}
