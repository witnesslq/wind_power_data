package com.jet.demo;

import com.jet.demo.mysql.entity.TimeWind;
import com.jet.demo.mysql.entity.TurbineData;
import com.jet.demo.mysql.entity.TurbineEquip;
import com.jet.demo.mysql.repository.TimeWindRepository;
import com.jet.demo.mysql.repository.TurbineEquipRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 15:30.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private TimeWindRepository timeWindRepository;

    @Test
    public void test1(){
        List<TimeWind> result = timeWindRepository.findTop1000();
        System.out.println(result);
    }
}
