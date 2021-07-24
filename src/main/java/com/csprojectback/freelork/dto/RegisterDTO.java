package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    private String dateRegister;

    @JsonView(ViewModel.Internal.class)
    private int timeRegister;

    @JsonView(ViewModel.Internal.class)
    private int idProject;

    @JsonView(ViewModel.Internal.class)
    private String nameProject;

    private String description;

    private String imageId;

    private String imageUrl;

    private int idUser;

}
