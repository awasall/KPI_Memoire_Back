package com.example.jira.service;
import com.example.jira.service.impl.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
//@EnableScheduling
public class servTest  {
    public static
    UpdateService updateService;
    public servTest(UpdateService updateService) {
        this.updateService = updateService;
    }
    @Scheduled(cron="${cron.expression}")
    //@Scheduled(cron="0 0/5 * * * ?", zone="UTC")
    public static void tester(){
        updateService.updateDB().subscribe(
                success ->
                        log.info("GOT  CATEGORIES"),
                error -> log.info("CATEGORY ERROR : "+error.getMessage())
        );
 }
}
