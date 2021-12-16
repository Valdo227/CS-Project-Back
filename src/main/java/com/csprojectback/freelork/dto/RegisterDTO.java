package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    private String title;

    private String student;

    @JsonView(ViewModel.Internal.class)
    private String dateRegister;

    @JsonView(ViewModel.Internal.class)
    private int timeRegister;

    @JsonView(ViewModel.Internal.class)
    private int idProject;

    @JsonView(ViewModel.Internal.class)
    private String nameProject;

    @JsonView(ViewModel.Internal.class)
    private int status;

    private String description;

    private String imageId;

    private String imageUrl;

    private Integer idUser;

}