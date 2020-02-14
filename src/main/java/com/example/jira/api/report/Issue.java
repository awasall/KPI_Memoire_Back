package com.example.jira.api.report;

import com.example.jira.api.response.ApiResponse;

public class Issue extends ApiResponse<Issue> {
   /* private String expand ;*/
    private  int id ;
    private String key ;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

}

