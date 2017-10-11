package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.WaterTurbineData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/11 11:44.
 */
public interface WaterTurbineDataRepository extends JpaRepository<WaterTurbineData, Integer> {
    @Query(value = "SELECT * FROM water_turbine_data LIMIT ?1, ?2", nativeQuery = true)
    List<WaterTurbineData> findLimitByNumber(int start, int end);
}
