package com.jet.demo.service.impl;

import com.jet.demo.mysql.entity.*;
import com.jet.demo.mysql.repository.*;
import com.jet.demo.pojo.TurbineDataPojo;
import com.jet.demo.pojo.WindDataPojo;
import com.jet.demo.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/19 11:31.
 */
@Service
public class DataServiceImpl implements IDataService {

    @Autowired
    private TimeWindRepository timeWindRepository;
    @Autowired
    private WindResultRepository windResultRepository;
    @Autowired
    private WindDataRepository windDataRepository;
    @Autowired
    private WindDataAbnormalRepository windDataAbnormalRepository;
    @Autowired
    private WindDataFactoryRepository windDataFactoryRepository;
    @Autowired
    private TurbineEquipRepository turbineEquipRepository;
    @Autowired
    private TurbineDataRepository turbineDataRepository;

    @Override
    public List<TimeWind> getFirst1000Data() {
        return timeWindRepository.findTop1000();
    }

    @Override
    public List<TimeWind> getNextOne(int offset) {
        return timeWindRepository.findNext(offset);
    }

    @Override
    public WindDataPojo getWindData(String timeFrom, String toTime) {
        List<WindData> windData;
        if (timeFrom == null && toTime == null) {
            windData = windDataRepository.findAll();
        } else {
            windData = windDataRepository.findBySystemTimeBetween(timeFrom, toTime);
        }
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        List<WindResult> windResults = windResultRepository.findAll(sort);

        List<WindDataAbnormal> windDataAbnormals = windDataAbnormalRepository.findAll();
        Map<Long, WindDataAbnormal> map = new HashMap<>();
        windDataAbnormals.forEach(windDataAbnormal -> map.put(windDataAbnormal.getId(), windDataAbnormal));
        List<WindData> windDataNormal = new ArrayList<>();
        List<WindData> windDataException = new ArrayList<>();
        int size = windResults.size();
        for (WindData d : windData) {
            float speed = d.getWindSpeed();
            int i = size - 1;
            for (; i >= 0; i--) {
                if (speed >= windResults.get(i).getWindSpeed()) {
                    break;
                }
            }
            WindDataAbnormal abnormal = map.get(d.getId());
            if (abnormal != null) {
                d.setFaultCause(abnormal.toString());
            }
            if (i == size - 1 || i < 0) {
                windDataException.add(d);
            } else {
                WindResult result1 = windResults.get(i);
                WindResult result2 = windResults.get(i + 1);
                float down = getComputeValue(result1.getWindSpeed(), result1.getLineDown(),
                        result2.getWindSpeed(), result2.getLineDown(), speed);
                if (d.getPowerValid() < down) {
                    windDataException.add(d);
                } else {
                    float up = getComputeValue(result1.getWindSpeed(), result1.getLineUp(),
                            result2.getWindSpeed(), result2.getLineUp(), speed);
                    if (d.getPowerValid() > up) {
                        windDataException.add(d);
                    } else {
                        windDataNormal.add(d);
                    }
                }
            }
        }
        // 设置厂家曲线
        List<WindDataFactory> windDataFactories = windDataFactoryRepository.findAll();
        return new WindDataPojo(windResults, windDataNormal, windDataException, windDataFactories);
    }

    @Override
    public TurbineDataPojo first500Data(Integer id, Integer number) {
        //1. 获取设备
        TurbineEquip turbineEquip = turbineEquipRepository.findById(id);
        //2. 获取数据
        int start = 0, end = 864;
        if (number != null && number > 864) {
            start = number;
            end = 144;
        }
        List<TurbineData> turbineData = turbineDataRepository.findLimitByEquipName(turbineEquip.getName(), start, end);
        if (turbineData == null){
            return null;
        }
        List<List<Object>> values = new ArrayList<>();
        List<List<Object>> ucl = new ArrayList<>();
        List<List<Object>> x = new ArrayList<>();
        List<List<Object>> lcl = new ArrayList<>();
        for (TurbineData data : turbineData) {
            List<Object> list1 = new ArrayList<>();
            List<Object> list2 = new ArrayList<>();
            List<Object> list3 = new ArrayList<>();
            List<Object> list4 = new ArrayList<>();
            list1.add(data.getTime());
            list2.add(data.getTime());
            list3.add(data.getTime());
            list4.add(data.getTime());
            list1.add(data.getValue());
            list2.add(turbineEquip.getUcl());
            list3.add(turbineEquip.getX());
            list4.add(turbineEquip.getLcl());
            values.add(list1);
            ucl.add(list2);
            x.add(list3);
            lcl.add(list4);
        }
        TurbineDataPojo result = new TurbineDataPojo();
        result.setValues(values);
        result.setUcl(ucl);
        result.setX(x);
        result.setLcl(lcl);
        return result;
    }

    private float getComputeValue(float x1, float y1, float x2, float y2, float x3) {
        return (y2 - y1) / (x2 - x1) * (x3 - x1) + y1;
    }
}
