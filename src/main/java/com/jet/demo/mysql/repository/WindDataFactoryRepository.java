package com.jet.demo.mysql.repository;

import com.jet.demo.mysql.entity.WindDataFactory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/26 10:18.
 */
public interface WindDataFactoryRepository extends JpaRepository<WindDataFactory, Integer> {
}
