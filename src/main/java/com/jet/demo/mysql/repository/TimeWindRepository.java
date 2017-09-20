package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.TimeWind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 15:18.
 */
@Repository
public interface TimeWindRepository extends JpaRepository<TimeWind, Long> {

    @Query(value = "SELECT id,time,wind_speed,power,label FROM label_traindata LIMIT 1000", nativeQuery = true)
    List<TimeWind> findTop1000();

    @Query(value = "SELECT id,time,wind_speed,power,label FROM label_traindata LIMIT ?, 60", nativeQuery = true)
    List<TimeWind> findNext(int offSet);
}
