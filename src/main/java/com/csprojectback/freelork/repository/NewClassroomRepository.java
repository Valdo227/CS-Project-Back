package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.NewsClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewClassroomRepository extends JpaRepository<NewsClassroomEntity,Integer> {

}