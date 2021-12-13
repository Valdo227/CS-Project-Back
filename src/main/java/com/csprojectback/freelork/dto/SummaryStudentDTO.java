package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class SummaryStudentDTO {

    @JsonView(ViewModel.Internal.class)
    List<RegisterDTO> registers;

    @JsonView(ViewModel.Internal.class)
    int hours;

    List<ProjectRegistersDTO> projects;
}