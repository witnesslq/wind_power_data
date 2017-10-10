package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.TurboData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/10 15:45.
 */
@Repository
public interface TurboDataRepository extends JpaRepository<TurboData, Long> {

    @Query(value = "SELECT * FROM turbo_data LIMIT ?1, ?2", nativeQuery = true)
    List<TurboData> findLimitByNumber(int start, int end);
}
