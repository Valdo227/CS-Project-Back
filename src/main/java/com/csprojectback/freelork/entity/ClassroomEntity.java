package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "classroom")
public class ClassroomEntity implements Serializable {

    private static final long serialVersionUID = -7625141059278141122L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private String code;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name="id_teacher")
    private TeacherEntity teacherEntity;

    @ManyToOne()
    @JoinColumn(name = "id_class")
    @JsonView(ViewModel.Internal.class)
    private ClazzEntity clazzEntity;

    @OneToMany(mappedBy = "classroomEntity", cascade = CascadeType.ALL)
    private List<NewsClassroomEntity> newsClassroomEntities;

}
