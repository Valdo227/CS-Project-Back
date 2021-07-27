package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClazzDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String name;
}