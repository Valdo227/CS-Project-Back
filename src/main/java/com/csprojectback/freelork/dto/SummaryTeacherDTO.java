package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryTeacherDTO {

    @JsonView(ViewModel.Internal.class)
    List<RegisterDTO> registers;

    @JsonView(ViewModel.Internal.class)
    int students;

    @JsonView(ViewModel.Internal.class)
    int freeStudents;

    @JsonView(ViewModel.Internal.class)
    int groups;

    @JsonView(ViewModel.Internal.class)
    int totalTasks;
}
