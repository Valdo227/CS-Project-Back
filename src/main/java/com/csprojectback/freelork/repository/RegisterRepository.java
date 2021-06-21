package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity,Integer> {
}
