package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterTeacherDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    int idUser;

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

    @JsonView(ViewModel.Internal.class)
    String image;
}