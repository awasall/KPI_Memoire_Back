package com.example.jira.api.report;

import java.util.List;

public class Report {
    private Content contents ;
    public int stpEngage(){
        return contents.getCompletedIssuesEstimateSum() + contents.getIssuesNotCompletedEstimateSum();
    }

    public int stpRealise(){
        return contents.getCompletedIssuesEstimateSum();
    }

    public int  nbIssues(){
        return contents.getCompletedIssues().size()+contents.getIssuesNotCompletedInCurrentSprint().size();
    }

    public int usRealise(){
        int count=0;
        for (ReportIssue issue : contents.getCompletedIssues()) {
          if(issue.getTypeId().equals("10001"))
            count++;
        }
        return count ;
    }
    public int usEngage(){
        int count=0;
        for (ReportIssue issue : contents.getCompletedIssues()) {
          if(issue.getTypeId().equals("10001"))
            count++;
        }
        for (ReportIssue issue : contents.getIssuesNotCompletedInCurrentSprint()) {
          if(issue.getTypeId().equals("10001"))
            count++;
        }
        return count ;
    }

    public int bugs(){
        int count=0;
        for (ReportIssue issue : contents.getCompletedIssues()) {
            if(issue.getTypeId().equals("10004"))
                count++;
        }
        for (ReportIssue issue : contents.getIssuesNotCompletedInCurrentSprint()) {
            if(issue.getTypeId().equals("10004"))
                count++;
        }
        return count ;
    }


    public Content getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Report{" +
                "contents=" + contents +
                '}';
    }
}

class  ReportIssue {
private int id ;
private String key ;
private String typeName;
private String typeId ;

    public String getTypeId() {
        return typeId;
    }

    private boolean done ;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "ReportIssue{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", typeName='" + typeName + '\'' +
                ", done=" + done +
                '}';
    }
}

class ReportEstimation {
    int value ;
    String text ;

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ReportEstimation{" +
                "value=" + value +
                ", text='" + text + '\'' +
                '}';
    }

}
 class  Content {
    private List<ReportIssue> completedIssues ;
    private List<ReportIssue> issuesNotCompletedInCurrentSprint ;
    private ReportEstimation issuesNotCompletedEstimateSum;
    private ReportEstimation completedIssuesEstimateSum;

     public List<ReportIssue> getCompletedIssues() {
         return completedIssues;
     }

     public void setCompletedIssues(List<ReportIssue> completedIssues) {
         this.completedIssues = completedIssues;
     }

     public List<ReportIssue> getIssuesNotCompletedInCurrentSprint() {
         return issuesNotCompletedInCurrentSprint;
     }

     public void setIssuesNotCompletedInCurrentSprint(List<ReportIssue> issuesNotCompletedInCurrentSprint) {
         this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
     }

     public int getIssuesNotCompletedEstimateSum() {
         return issuesNotCompletedEstimateSum.value;
     }

     public void setIssuesNotCompletedEstimateSum(ReportEstimation issuesNotCompletedEstimateSum) {
         this.issuesNotCompletedEstimateSum = issuesNotCompletedEstimateSum;
     }

     public int  getCompletedIssuesEstimateSum() {
         return completedIssuesEstimateSum.value;
     }

     public void setCompletedIssuesEstimateSum(ReportEstimation completedIssuesEstimateSum) {
         this.completedIssuesEstimateSum = completedIssuesEstimateSum;
     }

     @Override
     public String toString() {
         return "Content{" +
                 "completedIssues=" + completedIssues +
                 ", issuesNotCompletedInCurrentSprint=" + issuesNotCompletedInCurrentSprint +
                 ", issuesNotCompletedEstimateSum=" + issuesNotCompletedEstimateSum +
                 ", completedIssuesEstimateSum=" + completedIssuesEstimateSum +
                 '}';
     }
 }