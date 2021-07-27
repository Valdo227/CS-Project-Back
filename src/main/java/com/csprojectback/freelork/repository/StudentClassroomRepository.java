package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ClassroomEntity;
import com.csprojectback.freelork.entity.StudentClassroomEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroomEntity,Integer> {

    Optional<StudentClassroomEntity> findByStudentEntityAndClassroomEntityAndStatusNot(StudentEntity studentEntity, ClassroomEntity classroomEntity,int status);
}
