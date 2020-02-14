package com.example.jira.service.dto.jiraApi;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SprintDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id ;
    private String self;
    private String state ;
    private String name ;
    private Date startDate ;
    private Date endDate ;
    private Date completeDate ;
    private int originBoardId ;
    private String goal ;

    private List<SprintDto> values ;

}
