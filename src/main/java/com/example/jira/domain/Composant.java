package com.example.jira.domain;
import java.util.Date;
import java.util.List;
public class Composant {

    private Paging paging;
    private List<Measure> measures;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}

class History {
    private Date date;
    private int value;

    public Date getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

}
 class Measure{

     private List<History> history;

     public String metric;

     public String getMetric() {
         return metric;
     }
     public List<History> getHistory() {
         return history;
     }

     public void setHistory(List<History> history) {
         this.history = history;
     }

     public void setMetric(String metric) {
         this.metric = metric;
     }

 }
 class Paging{
    int pageIndex;
    int pageSize;
    int total;

 }






