package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class SummaryTeacherDTO {

    @JsonView(ViewModel.Internal.class)
    List<RegisterTeacherDTO> registers;

    @JsonView(ViewModel.Internal.class)
    int students;

    @JsonView(ViewModel.Internal.class)
    int groups;

    @JsonView(ViewModel.Internal.class)
    int tasks;
}