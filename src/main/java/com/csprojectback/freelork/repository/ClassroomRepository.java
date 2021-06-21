package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity,Integer> {

}
