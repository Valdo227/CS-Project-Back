package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.AdvisorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorRepository extends JpaRepository<AdvisorEntity,Integer> {

    AdvisorEntity findById(int id);
}