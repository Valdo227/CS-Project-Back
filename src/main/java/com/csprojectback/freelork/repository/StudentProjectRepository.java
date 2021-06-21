package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.StudentProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProjectRepository extends JpaRepository<StudentProjectEntity,Integer> {
}
