package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherProfile {

    Integer id;

    @JsonView(ViewModel.Internal.class)
    String fullName;

    @JsonView(ViewModel.Internal.class)
    String email;

    @JsonView(ViewModel.Internal.class)
    String grade;

    @JsonView(ViewModel.Internal.class)
    String phone;

    @JsonView(ViewModel.Internal.class)
    String image;
}