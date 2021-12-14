package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity,Integer> {

    NewsEntity findById(int id);
}