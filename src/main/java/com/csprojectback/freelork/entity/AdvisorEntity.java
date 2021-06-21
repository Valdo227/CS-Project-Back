package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "advisor")
public class AdvisorEntity implements Serializable {

    private static final long serialVersionUID = 5095365541781927707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private String name;

    @JsonView(ViewModel.Internal.class)
    private String lastname;

    @JsonView(ViewModel.Internal.class)
    private String grade;

    @JsonView(ViewModel.Internal.class)
    private String phone;

    @JsonView(ViewModel.Internal.class)
    private String email;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateUpdated;


    @ManyToOne()
    @JoinColumn(name = "id_company")
    @JsonView(ViewModel.Internal.class)
    private CompanyEntity companyEntity;


}
