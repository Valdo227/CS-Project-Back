package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "teacher")
public class TeacherEntity implements Serializable {

    private static final long serialVersionUID = -4692176423808653039L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Public.class)
    private String grade;

    @JsonView(ViewModel.Public.class)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonView(ViewModel.Internal.class)
    private UserEntity userEntity;


}
