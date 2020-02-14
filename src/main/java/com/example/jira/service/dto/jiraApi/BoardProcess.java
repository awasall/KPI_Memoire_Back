package com.example.jira.service.dto.jiraApi;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BoardProcess {
    private List<BoardDto> values;
}
