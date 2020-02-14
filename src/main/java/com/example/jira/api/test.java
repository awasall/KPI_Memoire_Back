package com.example.jira.api;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

import com.example.jira.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

public class test {
    public static  long countWeekDaysMath ( LocalDate start , LocalDate stop ) {
        // Code taken from Answer by Roland.
        // https://stackoverflow.com/a/44942039/642706
        long count = 0;
        final DayOfWeek startW = start.getDayOfWeek();
        final DayOfWeek stopW = stop.getDayOfWeek();

        final long days = ChronoUnit.DAYS.between( start , stop );
        final long daysWithoutWeekends = days - 2 * ( ( days + startW.getValue() ) / 7 );

        //adjust for starting and ending on a Sunday:
        count = daysWithoutWeekends + ( startW == DayOfWeek.SUNDAY ? 1 : 0 ) + ( stopW == DayOfWeek.SUNDAY ? 1 : 0 );

        return count;
    }
    @Autowired
    public static  SprintRepository sprint ;
    public static void main(String[] args) {

/*
        // Getting all projects
        List<ProjectProcess> projects = new ArrayList<>();
        WebClient client = (new Client()).getClient() ;
        Flux<ProjectProcess> response = client.get()
                .uri("/rest/api/2/project")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(ProjectProcess.class));
        response.subscribe(project -> System.out.println(project));

 */
   /*
        // Getting all projects
        List<ProjectProcess> projects = new ArrayList<>();
        WebClient client = (new Client()).getClient() ;
        Flux<UserTest> response = client.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(UserTest.class);
        response.subscribe(project -> System.out.println("TEST"));

*/

/*

        ProjectProcess aProject = new ProjectProcess("11703","ESHOPB2C","Eshop B2C Board");
        //Project aProject = new Project("10002","EDPS","Exemple de projet Scrum");
        // Getting Boards of a project
        //List<Board> boardList = projects.get(0).getAllBoard();;
        List<BoardProcess> boardList = aProject.getAllBoard();;
        System.out.println("BOARD LIST :");
        for (BoardProcess board : boardList) {
            System.out.println("\n"+board);
        }

        // Getting Sprint of the first board of the project
        System.out.println("BOARD SPRINT  LIST:  ");
        List<SprintProcess> boardSprintList = boardList.get(0).getAllSprint();
        System.out.println(boardSprintList.size() +" boardSprintList :  ");
        int count=-1;
        for (SprintProcess temp : boardSprintList) {
            count++;
            System.out.println("COUNT : "+count+"\n"+temp);
        }





        // issues of the first sprint
       // ISSUE
       // List<Issue> boardSprintIssueList = boardSprintList.get(10).getAllIssue();

        for (SprintProcess temp : boardSprintList) {
            System.out.println(temp.results());
        }
      //  System.out.println(boardSprintList.get(10).issueTypes());
*/


        /*Optional <Sprint> aSprint = sprint.findById(728);
        aSprint.ifPresent(sprint1 -> {
            System.out.println(sprint1);
        });
        LocalDate start = LocalDate.of( 2016 , 1 , 23 );
        LocalDate stop = start.plusMonths( 1 );

        System.out.println(countWeekDaysMath(start, stop ));
        */

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse("2019-05-21T09:36:00.000Z", inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(LocalDate.now());
    }

}
