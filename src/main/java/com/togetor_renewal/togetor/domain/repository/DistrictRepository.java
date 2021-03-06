package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllBySiDoAndSiGunGuIsNotNullAndEupMyeonDongIsNull(String siDo);
    List<District> findAllBySiDoAndSiGunGuAndEupMyeonDongIsNotNull(String siDo, String siGunGu);

}
