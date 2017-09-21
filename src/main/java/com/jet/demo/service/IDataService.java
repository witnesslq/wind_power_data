package com.jet.demo.service;

import com.jet.demo.mysql.entity.TimeWind;
import com.jet.demo.pojo.WindDataPojo;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/19 11:26.
 */
public interface IDataService {
    List<TimeWind> getFirst1000Data();

    List<TimeWind> getNextOne(int offset);

    WindDataPojo getWindData(String timeFrom, String toTime);
}