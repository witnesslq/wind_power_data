package com.jet.demo.service.impl;

import com.jet.demo.mysql.entity.*;
import com.jet.demo.mysql.repository.*;
import com.jet.demo.pojo.BlowerDataPojo;
import com.jet.demo.pojo.WaterTurbineDataPojo;
import com.jet.demo.pojo.WindDataPojo;
import com.jet.demo.service.IDataService;
import com.jet.demo.utils.MathUtil;
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
    @Autowired
    private WindDataFactoryRepository windDataFactoryRepository;
    @Autowired
    private TurbineEquipRepository turbineEquipRepository;
    @Autowired
    private TurbineDataRepository turbineDataRepository;
    @Autowired
    private GearboxEquipRepository gearboxEquipRepository;
    @Autowired
    private GearboxDataRepository gearboxDataRepository;
    @Autowired
    private TurboDataRepository turboDataRepository;
    @Autowired
    private WaterTurbineDataRepository waterTurbineDataRepository;

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
    public BlowerDataPojo turbineData(Integer id, Integer number) {
        //1. 获取设备
        TurbineEquip turbineEquip = turbineEquipRepository.findById(id);
        //2. 获取数据
        int start = 0, end = 864;
        if (number != null && number > 864) {
            start = number - 144;
            end = 144;
        }
        List<TurbineData> turbineData = turbineDataRepository.findLimitByEquipName(turbineEquip.getEquipName(), start, end);
        if (turbineData == null) {
            return null;
        }
        List<List<Object>> valuesLine = new ArrayList<>();
        List<List<Object>> uclLine = new ArrayList<>();
        List<List<Object>> xLine = new ArrayList<>();
        List<List<Object>> lclLine = new ArrayList<>();
        List<List<Object>> errorLine = new ArrayList<>();
        for (TurbineData data : turbineData) {
            List<Object> list1 = new ArrayList<>();
            List<Object> list2 = new ArrayList<>();
            List<Object> list3 = new ArrayList<>();
            List<Object> list4 = new ArrayList<>();
            List<Object> list5 = new ArrayList<>();
            list1.add(data.getTime());
            list2.add(data.getTime());
            list3.add(data.getTime());
            list4.add(data.getTime());
            list5.add(data.getTime());
            list1.add(data.getValue());
            list2.add(turbineEquip.getUcl());
            list3.add(turbineEquip.getX());
            list4.add(turbineEquip.getLcl());
            if (data.getValue() > turbineEquip.getUcl()) {
                list5.add(data.getValue());
                errorLine.add(list5);
            } else {
                list5.add(null);
                errorLine.add(list5);
            }
            valuesLine.add(list1);
            uclLine.add(list2);
            xLine.add(list3);
            lclLine.add(list4);
        }
        BlowerDataPojo result = new BlowerDataPojo();
        result.setUCL(turbineEquip.getUcl());
        result.setX(turbineEquip.getX());
        result.setLCL(turbineEquip.getLcl());
        result.setValuesLine(valuesLine);
        result.setUclLine(uclLine);
        result.setxLine(xLine);
        result.setLclLine(lclLine);
        result.setErrorLine(errorLine);
        return result;
    }

    @Override
    public BlowerDataPojo gearboxData(Integer id, Integer number) {
        //1. 获取设备
        GearboxEquip gearboxEquip = gearboxEquipRepository.findById(id);
        //2. 获取数据
        int start = 0, end = 864;
        if (number != null && number > 864) {
            start = number;
            end = 144;
        }
        List<GearboxData> gearboxData = gearboxDataRepository.findLimitByEquipName(gearboxEquip.getEquipName(), start, end);
        if (gearboxData == null) {
            return null;
        }
        List<List<Object>> valuesLine = new ArrayList<>();
        List<List<Object>> uclLine = new ArrayList<>();
        List<List<Object>> xLine = new ArrayList<>();
        List<List<Object>> lclLine = new ArrayList<>();
        List<List<Object>> errorLine = new ArrayList<>();
        for (GearboxData data : gearboxData) {
            List<Object> list1 = new ArrayList<>();
            List<Object> list2 = new ArrayList<>();
            List<Object> list3 = new ArrayList<>();
            List<Object> list4 = new ArrayList<>();
            List<Object> list5 = new ArrayList<>();
            list1.add(data.getTime());
            list5.add(data.getTime());
            list2.add(data.getTime());
            list3.add(data.getTime());
            list4.add(data.getTime());
            list1.add(MathUtil.keepDigitsAfterPoint(data.getValue(), 4));
            list2.add(gearboxEquip.getUcl());
            list3.add(gearboxEquip.getX());
            list4.add(gearboxEquip.getLcl());
            if (data.getValue() > gearboxEquip.getUcl()) {
                list5.add(MathUtil.keepDigitsAfterPoint(data.getValue(), 4));
                errorLine.add(list1);
            } else {
                list5.add(null);
                errorLine.add(list5);
            }
            valuesLine.add(list1);
            uclLine.add(list2);
            xLine.add(list3);
            lclLine.add(list4);
        }
        BlowerDataPojo result = new BlowerDataPojo();
        result.setUCL(gearboxEquip.getUcl());
        result.setX(gearboxEquip.getX());
        result.setLCL(gearboxEquip.getLcl());
        result.setValuesLine(valuesLine);
        result.setUclLine(uclLine);
        result.setxLine(xLine);
        result.setLclLine(lclLine);
        result.setErrorLine(errorLine);
        return result;
    }

    @Override
    public List<List<Object>> turboData(Integer type, Integer number) {
        // 查询数据
        int start = 0, end = 1000;
        if (number != null && number > 1000) {
            start = number - 1000;
            end = 1000;
        }
        List<TurboData> turboDataList = turboDataRepository.findLimitByNumber(start, end);
        if (turboDataList == null || turboDataList.size() == 0) {
            return null;
        }
        // 包装数据
        List<List<Object>> result = new ArrayList<>();
        if (type == 1) {
            // 获取vortex_strip
            turboDataList.forEach(turboData -> {
                List<Object> list = new ArrayList<>();
                list.add(String.format("%.4E", Double.parseDouble(turboData.getT())));
                list.add(MathUtil.keepDigitsAfterPoint(turboData.getVortexStrip(), 1));
                result.add(list);
            });
        } else if (type == 2) {
            // 获取steady_state
            turboDataList.forEach(turboData -> {
                List<Object> list = new ArrayList<>();
                list.add(String.format("%.4E", Double.parseDouble(turboData.getT())));
                list.add(MathUtil.keepDigitsAfterPoint(turboData.getSteadyState(), 1));
                result.add(list);
            });
        }
        return result;
    }

    @Override
    public WaterTurbineDataPojo waterTurbine(Integer number) {
        int start = 0, end = 100;
        if (number != null && number > 100) {
            start = number - 50;
            end = 50;
        }
        List<WaterTurbineData> waterTurbineDataList = waterTurbineDataRepository.findLimitByNumber(start, end);
        WaterTurbineDataPojo result = new WaterTurbineDataPojo();
        waterTurbineDataList.forEach(waterTurbineData -> {
            List<Object> x1 = new ArrayList<>();
            List<Object> x2 = new ArrayList<>();
            List<Object> x3 = new ArrayList<>();
            List<Object> y1 = new ArrayList<>();
            List<Object> y2 = new ArrayList<>();
            List<Object> y3 = new ArrayList<>();
            x1.add(waterTurbineData.getTime().getTime());
            x2.add(waterTurbineData.getTime().getTime());
            x3.add(waterTurbineData.getTime().getTime());
            y1.add(waterTurbineData.getTime().getTime());
            y2.add(waterTurbineData.getTime().getTime());
            y3.add(waterTurbineData.getTime().getTime());
            x1.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getX1(), 3));
            x2.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getX2(), 3));
            x3.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getX3(), 3));
            y1.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getY1(), 3));
            y2.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getY2(), 3));
            y3.add(MathUtil.keepDigitsAfterPoint(waterTurbineData.getY3(), 3));
            result.getX1Line().add(x1);
            result.getX2Line().add(x2);
            result.getX3Line().add(x3);
            result.getY1Line().add(y1);
            result.getY2Line().add(y2);
            result.getY3Line().add(y3);
        });
        return result;
    }


    private float getComputeValue(float x1, float y1, float x2, float y2, float x3) {
        return (y2 - y1) / (x2 - x1) * (x3 - x1) + y1;
    }
}
