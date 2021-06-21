package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "company")
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = 7021245249508590458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Public.class)
    private String serviceType;

    @JsonView(ViewModel.Public.class)
    private String sizeCompany;

    @JsonView(ViewModel.Public.class)
    private String address;

    @JsonView(ViewModel.Public.class)
    private String hrName;

    @JsonView(ViewModel.Public.class)
    private String hrLastname;

    @JsonView(ViewModel.Public.class)
    private String hrPhone;

    @JsonView(ViewModel.Public.class)
    private String hrEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonView(ViewModel.Internal.class)
    private UserEntity userEntity;

}
