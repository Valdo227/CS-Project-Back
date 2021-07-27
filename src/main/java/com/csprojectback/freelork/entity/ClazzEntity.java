package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "class")
public class ClazzEntity implements Serializable {

    private static final long serialVersionUID = -851804058301288213L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Public.class)
    private String carrerName;

    @JsonView(ViewModel.Public.class)
    private String schedule;

    @JsonView(ViewModel.Public.class)
    private int grade;

    @JsonView(ViewModel.Public.class)
    private String groupClass;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateUpdated;


}
