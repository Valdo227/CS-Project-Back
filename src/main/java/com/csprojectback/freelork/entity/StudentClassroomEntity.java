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
@Table(name = "student_classroom")
public class StudentClassroomEntity implements Serializable {

    private static final long serialVersionUID = -2381703292434417321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateUpdated;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student")
    @JsonView(ViewModel.Internal.class)
    StudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_classroom")
    @JsonView(ViewModel.Internal.class)
    ClassroomEntity classroomEntity;

}
