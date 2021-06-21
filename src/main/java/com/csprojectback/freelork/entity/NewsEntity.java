package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "news")
public class NewsEntity implements Serializable {

    private static final long serialVersionUID = 6176986150055394916L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    private String description;

    @JsonView(ViewModel.Internal.class)
    private LocalDate dateStart;

    @JsonView(ViewModel.Internal.class)
    private LocalDate dateEnd;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateUpdated;

    @ManyToOne()
    @JoinColumn(name = "id_admin")
    @JsonView(ViewModel.Internal.class)
    private AdminEntity adminEntity;

    @OneToMany(mappedBy = "newsEntity", cascade = CascadeType.ALL)
    private  List<NewsClassroomEntity> newsClassroomEntities;

}
