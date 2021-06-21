package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news_classroom")
public class NewsClassroomEntity implements Serializable {

    private static final long serialVersionUID = 2987962630149649632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewModel.Internal.class)
    private int id;

    @JsonView(ViewModel.Internal.class)
    private int status;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateCreated;

    @JsonView(ViewModel.Internal.class)
    private LocalDateTime dateUpdated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_news")
    @JsonView(ViewModel.Internal.class)
    NewsEntity newsEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_classroom")
    @JsonView(ViewModel.Internal.class)
    ClassroomEntity classroomEntity;
}
