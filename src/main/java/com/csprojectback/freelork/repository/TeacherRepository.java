package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.TeacherEntity;
import com.csprojectback.freelork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Integer> {

    TeacherEntity findById(int id);

}
