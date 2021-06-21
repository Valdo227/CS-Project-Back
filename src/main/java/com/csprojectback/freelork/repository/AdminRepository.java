package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Integer> {

    AdminEntity findById(int id);
}
