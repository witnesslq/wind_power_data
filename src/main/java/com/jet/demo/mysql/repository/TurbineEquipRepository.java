package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.TurbineEquip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 9:27.
 */
@Repository
public interface TurbineEquipRepository extends JpaRepository<TurbineEquip, Integer> {

    TurbineEquip findByEquipName(String equipName);

    TurbineEquip findById(int id);
}
