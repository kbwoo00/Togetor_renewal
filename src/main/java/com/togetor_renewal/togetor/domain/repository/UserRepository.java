package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
