package com.togetor_renewal.togetor.domain.repository;


import com.togetor_renewal.togetor.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
