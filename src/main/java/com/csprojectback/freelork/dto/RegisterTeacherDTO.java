package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterTeacherDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    private String dateRegister;

    @JsonView(ViewModel.Internal.class)
    private String student;

    @JsonView(ViewModel.Internal.class)
    private String nameCompany;

    @JsonView(ViewModel.Internal.class)
    private int timeRegister;

    @JsonView(ViewModel.Internal.class)
    private String nameProject;

    @JsonView(ViewModel.Internal.class)
    private int status;
}
