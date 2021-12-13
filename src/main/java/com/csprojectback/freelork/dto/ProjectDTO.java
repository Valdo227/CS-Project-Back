package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class ProjectDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String imageUrl;

    String imageId;

    @JsonView(ViewModel.Internal.class)
    String name;

    @JsonView(ViewModel.Internal.class)
    String dateCreated;

    @JsonView(ViewModel.Internal.class)
    String objectives;

    @JsonView(ViewModel.Internal.class)
    int status;

    int idUser;
}