package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.GearboxData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 9:06.
 */
@Repository
public interface GearboxDataRepository extends JpaRepository<GearboxData, Long> {

    @Query(value = "SELECT * FROM gearbox_data a WHERE a.equip_name = ?1 LIMIT ?2, ?3", nativeQuery = true)
    List<GearboxData> findLimitByEquipName(String equipName, int start, int end);
}
