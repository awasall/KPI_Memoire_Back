package com.example.jira.service.dto;
import lombok.*;
import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key ;
    private String name ;
    private String emailAddress;
    private String displayName;
    private HashMap <String,String> avatarUrls ;
    private String token;

}

