package com.csprojectback.freelork.repository;

import com.csprojectback.freelork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findById(int id);

    Optional<UserEntity> findByEmail(String email);
}