package com.example.jira.web.controller;

import com.example.jira.domain.Sprint;
import com.example.jira.repository.SprintRepository;
import com.example.jira.service.dto.SprintDTO;
import com.example.jira.web.exceptions.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(description = "API pour les opérations CRUD sur les Sprints.")

@RestController
public class SprintController {
    @Autowired
    private SprintRepository sprintRepository;

    @ApiOperation(value = "This get a sprint by his id (if it exists)")
    @GetMapping(value = "/sprint/{id}")

    public Sprint getSprint(@PathVariable Integer id) {

        Optional<Sprint> sprint = sprintRepository.findById(id);
        Sprint theSprint = sprint.get();
        return theSprint;
    }

    @ApiOperation(value = "This get all sprints of a specific board by board_id")
    @GetMapping(value = "/sprint/board/{boardId}")
    public Page<SprintDTO> getProjectBoardSprints(
            @PathVariable Integer boardId,
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "startDate", direction = Sort.Direction.DESC),
                    // @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @RequestParam(value = "state", required = false) final String state //for filtering purpose
    ) {
        System.out.println(pageable);
        //System.out.println(state);
        if (state == null)
            return convertToSprintDTO(sprintRepository.findByBoardId(boardId, pageable));
        else
            return convertToSprintDTO(sprintRepository.findByBoardIdAndState(boardId, state, pageable));

    }

    private Page<SprintDTO> convertToSprintDTO(Page<Sprint> sprintPages) {
        return
                sprintPages
                        .map(sprint -> {
                            SprintDTO asprintDto = new SprintDTO();
                            BeanUtils.copyProperties(sprint, asprintDto);
                            asprintDto.setProjectName(sprint.getBoard().getProject().getName());
                            return asprintDto;
                        });
    }


    @GetMapping(value = "/sprint/dashboard/")
    public Page<SprintDTO> getSprintsMonth(@PageableDefault(page = 0, size = 10)
                                                   // @SortDefault.SortDefaults({
                                                   //      @SortDefault(sort = "projectName", direction = Sort.Direction.ASC)})
                                                   Pageable pageable,
                                           @RequestParam(value = "startPeriod", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startPeriod,
                                           @RequestParam(value = "endPeriod", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endPeriod,
                                           @RequestParam(value = "state", required = false) final String state
    ) {
        final LocalDate today = LocalDate.now();
        if (startPeriod == null)
            //return convertToSprintDTO(sprintRepository.findByStateEquals("active", pageable));
            if (state == null)
                return convertToSprintDTO(sprintRepository.findByEndDateGreaterThanEqualAndStartDateLessThanEqual(today, today, pageable));
            else
                return convertToSprintDTO(sprintRepository.findByEndDateGreaterThanEqualAndStartDateLessThanEqualAndState(today, today, state, pageable));
        else if (state != null)
            return convertToSprintDTO(sprintRepository.findByEndDateGreaterThanEqualAndStartDateLessThanEqualAndState(endPeriod, startPeriod, state, pageable));
        else
            return convertToSprintDTO(sprintRepository.findByEndDateGreaterThanEqualAndStartDateLessThanEqual(endPeriod, startPeriod, pageable));
    }
}

