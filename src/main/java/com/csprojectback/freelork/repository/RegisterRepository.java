package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ProjectEntity;
import com.csprojectback.freelork.entity.RegisterEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity,Integer> {

    RegisterEntity findById(int id);

    List<RegisterEntity> findByStudentEntityAndStatusNot(StudentEntity studentEntity,int status);

    List<RegisterEntity> findByStudentEntityAndStatusNotAndDateRegisterBetween(StudentEntity studentEntity,int status, LocalDate date1, LocalDate date2);

    List<RegisterEntity> findByStudentEntityAndStatusNotOrderByIdDesc(StudentEntity studentEntity,int status);

    List<RegisterEntity> findByProjectEntityAndStatusNotAndStudentEntity(ProjectEntity projectEntity, int status, StudentEntity studentEntity);
}
