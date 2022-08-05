package com.example.jira.domain;

import com.example.jira.api.report.Report;
import com.example.jira.repository.SprintRepository;
import com.fasterxml.jackson.annotation.*;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIdentityReference(alwaysAsId = true)

public class Sprint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id ;
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // private Long sprintId;

    private String self;
    private String state ;
    private String name ;

    //@JsonFormat(shape= JsonFormat.Shape.STRING , pattern = "dd-MM-yy hh:mm:ss")
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate ;

   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
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
    //private  Date date;
    private int detteTechnique;
//methode pour calculer les stp engagés et réalisées .....de chaque sprint
    public Sprint setReport (Report report){
            stpEngage = report.stpEngage();
            stpRealise = report.stpRealise() ;
            nbIssues = report.nbIssues();
            usRealise = report.usRealise();
            usEngage = report.usEngage() ;
            bugs = report.bugs();
            return this;
    }
 //Méthode pour l'historique des dettes techniques


    /**
     * Calcule la liste des durées entre deux sprints consécutifs en jours ouvrables
     * @param spp
     * @param id
     * @return
     */
    public List<Integer> liste(SprintRepository spp, int id) {
        //recupérer l'ensemble des sprints qui ont un mm board_id et l'ordonner par date de début
        List<Sprint> sprints = spp.findByBoardIdOrderById(id);
        //ensuite parcourir la liste recupérée
        //calculer la durée par paire de sprint de la liste déjà triée
        //on prend la fin du sprint 1 et le début du sprint 2
        // en omettant les jours non ouvrés
        //List<Sprint> sprints = spp.findByBoardIdOrderByStartDate(id);
        return recurpererLaListeDesDureesEntreSprint(spp,sprints);
    }

    public List<Integer> recurpererLaListeDesDureesEntreSprint(SprintRepository spp, List<Sprint> sprints) {
        List<Integer> resultat = new LinkedList<>();
        for (int i = 0; i < sprints.size()-1; i++) {
            Sprint sprint1 = sprints.get(i);
            Sprint sprint2 = sprints.get(i+1);

            Date endDate = sprint1.getEndDate();//df1
            Date startDate = sprint2.getStartDate();//dd2
            if(startDate==null || endDate==null){
                dureeEntreDeuxSprint=0;
            }

            else{
            int duree = caculerLesJoursOuvresEntre(startDate, endDate);

            if(i==0  ){
                sprint1.setDureeEntreDeuxSprint(0);
                spp.save(sprint1);
            }

            sprint2.setDureeEntreDeuxSprint(duree);
            spp.save(sprint2);

            resultat.add(caculerLesJoursOuvresEntre(startDate, endDate));

        }}
        return resultat;
    }
    private int caculerLesJoursOuvresEntre(Date debut, Date fin){
        if(startDate==null || endDate==null){
            dureeEntreDeuxSprint=0;
        }
        else{
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(fin);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(debut);
        dureeEntreDeuxSprint=0;
        while (calendar1.getTimeInMillis() < calendar2.getTimeInMillis()) {
            //excluding start date
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            {
                ++dureeEntreDeuxSprint;
            }
        } //excluding end date
        }
        return dureeEntreDeuxSprint;
    }


    //Calculons l'accération
    public List<Integer> accelerer(SprintRepository spp, List<Sprint> sprints) {
        List<Integer> resultat = new LinkedList<>();
        for( int i=3;i<sprints.size();i++){
            Sprint sprint3 = sprints.get(i);
            Sprint sprint2 = sprints.get(i-1);
            Sprint sprint1 = sprints.get(i-2);
            Sprint sprint0 = sprints.get(i-3);
            float moy=(float)(sprints.get(i-3).getStpRealise()+sprints.get(i-2).
                    getStpRealise()+sprints.get(i-1).getStpRealise())/3;
            if(sprints.get(i).getStpRealise()==0 ){
                acceleration=0;
            }
            else if (moy == 0) {
                acceleration=0;
            }
            else if(sprints.get(i).getStpRealise()==0){
                acceleration=0;
            }
            else{
                acceleration= (int) (((sprints.get(i).getStpRealise()/moy))*100);
            }
            if(i==3){
                sprint0.setAcceleration(0);
                sprint1.setAcceleration(0);
                sprint2.setAcceleration(0);
                spp.save(sprint0);
                spp.save(sprint1);
                spp.save(sprint2);
            }
            sprint3.setAcceleration(acceleration);
            spp.save(sprint3);
            resultat.add((int) acceleration);
        }
        return resultat;
    }
    //regrouper les sprints qui ont un meme boardId et et l'ordonner par id
    public List<Integer> calculAccele(SprintRepository spp,int id){
        List<Sprint> sprints = spp.findByBoardIdOrderById(id);
        return accelerer(spp,sprints);
    }








    //Calculons duree du'un sprint
    public int dureSpr(){
        if(startDate==null || endDate==null){
            dureeSprint=0;
        }
        else {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            while (c1.before(c2)) {
                if ((Calendar.SATURDAY != c1.get(Calendar.DAY_OF_WEEK))
                        && (Calendar.SUNDAY != c1.get(Calendar.DAY_OF_WEEK))) {
                    dureeSprint++; }
                c1.add(Calendar.DATE, 1); }
        }
        return dureeSprint;
    }


    public void kpi (){
        if(stpEngage>0)
        completude = (int) Math.round((double)stpRealise/stpEngage*100 );
        if(usRealise>0)
        acceptanceUs =(int) Math.round((double)usRealise/usEngage*100 );
        //dureeSprint =
    }

    @ManyToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Board board ;
}
