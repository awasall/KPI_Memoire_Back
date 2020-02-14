package com.example.jira.web.controller;


import com.example.jira.service.impl.UpdateService;
import com.example.jira.service.servTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api( description="API pour la mise à jour de la base de données.")
@RestController
@Slf4j
public class UpdateController implements CommandLineRunner {

    public final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @ApiOperation(value = "This updates the database (this is automatically run in the start")
    @GetMapping(value = "/update")
    // Running the script
   // @Override
    public void run(String... args) {
        servTest.tester();
//        updateService.updateDB().subscribe(
//                success -> log.info("GOT  CATEGORIES"),
//                error -> log.info("CATEGORY ERROR : "+error.getMessage())
//        );
    }
}
