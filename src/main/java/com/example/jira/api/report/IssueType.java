package com.example.jira.api.report;

class  commonFields {
    private String self ;
    private int id ;
    private String name ;
    private String iconUrl;

    public String getSelf() {
        return self;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public String toString() {
        return "{id: "+id+" self : "+self + " name : "+name+"}";
    }
}


public class IssueType extends  commonFields{
    String description ;
    int avatarId ;
    boolean subtask ;
}