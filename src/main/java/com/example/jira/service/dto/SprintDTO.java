package com.example.jira.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SprintDTO {
    private int id ;
    private String self;
    private String state ;
    private String name ;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate ;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate ;
    private Date completeDate ;

    private int originBoardId ;
    private String goal ;

    // MAIN ATTRIBUTES
    private int stpEngage ;
    private int stpRealise;
    private int nbIssues;
    private int usRealise;
    private int usEngage;
    private int bugs ;

    // KPIs
    private int completude ;
    private int acceptanceUs ;
    private int acceleration ;
    private int velocite;
    private int dureeSprint;
    private int dureeEntreDeuxSprint;

    // CUSToMIZED
    private  String projectName ;
}
