package com.example.jira.repository;


import com.example.jira.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByOrderByIdAsc();

    List<Project> findByNameContainingIgnoreCase(String name);
    Page<Project> findByProjectCategoryId(Long id,Pageable pageable);
    //Page<Project> findByProjectCategoryId(Long id, Pageable pageable);
    Page<Project> findByProjectCategoryIdIsNull(Pageable pageable);
    /*@Query("SELECT p FROM project p WHERE p.project_category_id = :idCat ")
    Project getProjectsCategory(
            @Param("idCat") Integer id);*/
}
