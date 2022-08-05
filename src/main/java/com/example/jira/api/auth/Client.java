package com.example.jira.api.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


public class Client {
    private  WebClient client ;
    private final String baseUrl = "http://jira.tools.orange-sonatel.com";
    public Client() {
            // Encode using basic encoder
           /* String token = Base64.getEncoder().encodeToString(
            "username:pwd".getBytes("utf-8")); */
        this.client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.set(HttpHeaders.AUTHORIZATION,
                        "Basic c3RnX2VwdF9kczppdGVhbTIwMTk="))
                .build();

    }
    public Client(  String token) {

            this.client = WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeaders(header -> header.set(HttpHeaders.AUTHORIZATION, "Basic "+token))
                    .build();
    }

   /* public List<Project> getAllProject(){
        WebClient client = (new Client()).getClient() ;

        Flux< Project> response = client.get()
                .uri("/rest/api/2/project")
                .retrieve()
                .bodyToFlux(Project.class);
        return response ;
    } */

    public WebClient getClient() {
        return client;
    }
}
