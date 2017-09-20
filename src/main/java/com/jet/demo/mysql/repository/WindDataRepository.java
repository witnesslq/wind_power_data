package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.WindData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 15:58.
 */
public interface WindDataRepository extends JpaRepository<WindData, Long> {

    @Query(value = "SELECT id,wind_speed,power_valid FROM wind_data WHERE system_time BETWEEN ?1 AND ?2", nativeQuery = true)
    List<WindData> findBySystemTimeBetween(String timeFrom, String toTime);
}
