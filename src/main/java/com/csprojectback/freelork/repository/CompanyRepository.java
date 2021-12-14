package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Integer> {

    CompanyEntity findById(int id);
}