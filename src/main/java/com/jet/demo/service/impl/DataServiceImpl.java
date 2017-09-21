package com.jet.demo.service.impl;

import com.jet.demo.mysql.entity.TimeWind;
import com.jet.demo.mysql.entity.WindData;
import com.jet.demo.mysql.entity.WindDataAbnormal;
import com.jet.demo.mysql.entity.WindResult;
import com.jet.demo.mysql.repository.TimeWindRepository;
import com.jet.demo.mysql.repository.WindDataAbnormalRepository;
import com.jet.demo.mysql.repository.WindDataRepository;
import com.jet.demo.mysql.repository.WindResultRepository;
import com.jet.demo.pojo.WindDataPojo;
import com.jet.demo.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        return new WindDataPojo(windResults, windDataNormal, windDataException);
    }

    private float getComputeValue(float x1, float y1, float x2, float y2, float x3) {
        return (y2 - y1) / (x2 - x1) * (x3 - x1) + y1;
    }
}
