package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class StudentFullProfileDTO {

    StudentProfileDTO student;

    @JsonView(ViewModel.Internal.class)
    List<ProjectRegistersDTO> projects;
}