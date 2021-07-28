package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyProfileDTO {

    int id;

    @JsonView(ViewModel.Internal.class)
    String name;

    @JsonView(ViewModel.Internal.class)
    String email;

    @JsonView(ViewModel.Internal.class)
    String image;

    @JsonView(ViewModel.Internal.class)
    String address;

    @JsonView(ViewModel.Internal.class)
    String serviceType;

    @JsonView(ViewModel.Internal.class)
    String sizeCompany;

    @JsonView(ViewModel.Internal.class)
    String hrFullName;

    @JsonView(ViewModel.Public.class)
    private String hrPhone;

    @JsonView(ViewModel.Public.class)
    private String hrEmail;

}
