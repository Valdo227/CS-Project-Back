package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "register")
public class RegisterEntity implements Serializable {

    private static final long serialVersionUID = -8831076163985999048L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private LocalDate dateRegister;

    @JsonView(ViewModel.Internal.class)
    private LocalTime timeRegister;

    @JsonView(ViewModel.Internal.class)
    private String description;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateUpdated;

    @ManyToOne()
    @JoinColumn(name = "id_student")
    @JsonView(ViewModel.Internal.class)
    private StudentEntity studentEntity;

    @ManyToOne()
    @JoinColumn(name = "id_project")
    @JsonView(ViewModel.Internal.class)
    private ProjectEntity projectEntity;

}
