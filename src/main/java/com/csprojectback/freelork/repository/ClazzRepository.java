package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.ClazzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzRepository extends JpaRepository<ClazzEntity,Integer> {

    ClazzEntity findById(int id);
}
