package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.CompanyEntity;
import com.csprojectback.freelork.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    ProjectEntity findById(int id);

    List<ProjectEntity> findByCompanyEntity(CompanyEntity companyEntity);
}
