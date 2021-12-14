package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ClassroomEntity;
import com.csprojectback.freelork.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity,Integer> {

    Optional<ClassroomEntity> findByCodeAndStatusNot(String code, int status);

    List<ClassroomEntity> findByTeacherEntityAndStatusNot(TeacherEntity teacherEntity, int status);
}