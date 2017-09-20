package com.jet.demo.service.impl;

import com.jet.demo.mysql.entity.TimeWind;
import com.jet.demo.mysql.entity.WindData;
import com.jet.demo.mysql.entity.WindResult;
import com.jet.demo.mysql.repository.TimeWindRepository;
import com.jet.demo.mysql.repository.WindDataRepository;
import com.jet.demo.mysql.repository.WindResultRepository;
import com.jet.demo.pojo.WindDataPojo;
import com.jet.demo.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private static Long[] bladeIcing = new Long[]{1664L, 2320L, 2359L, 915L, 2330L, 2366L, 1331L, 1326L, 402L, 450L, 933L, 341L};
    private static Long[] gearboxFailure = new Long[]{342L, 488L, 1724L, 212L, 2352L, 78L, 1369L, 23L, 1662L, 1360L, 794L, 235L, 500L, 453L, 344L, 599L, 373L};

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

        List bladeIcingList = Arrays.asList(bladeIcing);
        List gearboxFailureList = Arrays.asList(gearboxFailure);
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
            if (bladeIcingList.contains(d.getId())) {
                d.setFaultCause("叶片结冰");
            }
            if (gearboxFailureList.contains(d.getId())) {
                d.setFaultCause("齿轮箱失效");
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
