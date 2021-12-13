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
@Table(name = "project")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = -5911718951194794396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private String name;

    @JsonView(ViewModel.Internal.class)
    private String objectives;

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
    @JoinColumn(name = "id_company")
    @JsonView(ViewModel.Internal.class)
    private CompanyEntity companyEntity;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL)
    private List<StudentProjectEntity> studentProjectEntities;
}