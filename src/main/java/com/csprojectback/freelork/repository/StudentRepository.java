package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.StudentEntity;
import com.csprojectback.freelork.entity.UserEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface StudentRepository extends JpaRepository<StudentEntity,Integer> {

    StudentEntity findById(int id);

    StudentEntity findByUserEntity(UserEntity userEntity);

}
