package com.csprojectback.freelork.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.csprojectback.freelork.model.ViewModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 4114740507185595203L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Public.class)
    private String userCode;

    @JsonView(ViewModel.Public.class)
    private String fullName;

    @JsonView(ViewModel.Public.class)
    private String email;

    @JsonView(ViewModel.Public.class)
    private String password;

    @JsonView(ViewModel.Public.class)
    private String role;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateUpdated;


}
