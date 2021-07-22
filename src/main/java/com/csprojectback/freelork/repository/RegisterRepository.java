package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.RegisterEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity,Integer> {

    List<RegisterEntity> findByStudentEntity(StudentEntity studentEntity);


    List<RegisterEntity> findByStudentEntityAndDateRegisterBetween(StudentEntity studentEntity, LocalDate date1,LocalDate date2);
}
