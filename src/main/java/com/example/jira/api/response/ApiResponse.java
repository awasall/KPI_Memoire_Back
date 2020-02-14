package com.example.jira.api.response;


import java.util.List;

public class ApiResponse <T>  extends ResponseHeader {
    private  List<T> values ;
    private  List<T> issues ;

    public List<T> getIssues() {
        return issues;
    }

    public List<T> getValues() {
        return values;
    }
}
