package com.csprojectback.freelork.dto;


import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyStudentsDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String fullName;

    @JsonView(ViewModel.Internal.class)
    String enrollment;

    @JsonView(ViewModel.Internal.class)
    String email;

    @JsonView(ViewModel.Internal.class)
    String image;

    @JsonView(ViewModel.Internal.class)
    String group;

    @JsonView(ViewModel.Internal.class)
    int hours;
}