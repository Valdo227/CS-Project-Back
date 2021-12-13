package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateRegister;

    @JsonView(ViewModel.Internal.class)
    private int timeRegister;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    private String description;

    @JsonView(ViewModel.Internal.class)
    private String imageId;

    @JsonView(ViewModel.Internal.class)
    private String imageUrl;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
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