package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SummaryCompanyDTO {

    @JsonView(ViewModel.Internal.class)
    int students;

    @JsonView(ViewModel.Internal.class)
    int hours;

    @JsonView(ViewModel.Internal.class)
    int acceptedTasks;

    @JsonView(ViewModel.Internal.class)
    int pendingTasks;

    @JsonView(ViewModel.Internal.class)
    List<RegisterCompanyDTO> registers;
}