package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class ClassroomTeacherDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String name;

    @JsonView(ViewModel.Internal.class)
    String code;

    @JsonView(ViewModel.Internal.class)
    String dateCreated;

    @JsonView(ViewModel.Internal.class)
    String career;

    @JsonView(ViewModel.Internal.class)
    int students;

    @JsonView(ViewModel.Internal.class)
    int status;
}