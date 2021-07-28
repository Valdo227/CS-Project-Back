package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ProjectEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import com.csprojectback.freelork.entity.StudentProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentProjectRepository extends JpaRepository<StudentProjectEntity,Integer> {

    Optional<StudentProjectEntity> findByStudentEntityAndProjectEntityAndStatusNot(StudentEntity studentEntity, ProjectEntity projectEntity, int status);
    List<StudentProjectEntity> findByStudentEntityAndStatusNot(StudentEntity studentEntity, int status);

}
