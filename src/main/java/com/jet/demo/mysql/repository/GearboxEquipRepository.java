package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.GearboxEquip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/10 11:23.
 */
@Repository
public interface GearboxEquipRepository extends JpaRepository<GearboxEquip, Long> {
    GearboxEquip findById(int id);
}
