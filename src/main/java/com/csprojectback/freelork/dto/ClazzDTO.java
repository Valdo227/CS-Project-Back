package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClazzDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String name;
}