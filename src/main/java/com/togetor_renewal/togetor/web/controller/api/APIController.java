package com.togetor_renewal.togetor.web.controller.api;

import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final DistrictRepository districtRepository;

    @PostMapping("/district")
    public List<District> getDistrict(){
        List<District> districtList = districtRepository.findAll();
        return districtList;
    }
}
