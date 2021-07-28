package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "student")
public class StudentEntity implements Serializable {

    private static final long serialVersionUID = -6597412304267506907L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Public.class)
    private String enrollment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonView(ViewModel.Internal.class)
    private UserEntity userEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_company")
    @JsonView(ViewModel.Internal.class)
    private CompanyEntity companyEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_classroom")
    @JsonView(ViewModel.Internal.class)
    private ClassroomEntity classroomEntity;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL)
    private List<StudentProjectEntity> studentProjectEntities;


}
