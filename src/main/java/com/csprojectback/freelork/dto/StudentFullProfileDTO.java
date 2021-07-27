package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentFullProfileDTO {

    StudentProfileDTO student;

    @JsonView(ViewModel.Internal.class)
    List<RegisterDTO> registers;

    @JsonView(ViewModel.Internal.class)
    List<ProjectRegistersDTO> projects;


}
