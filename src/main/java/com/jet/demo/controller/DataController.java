package com.jet.demo.controller;

import com.jet.demo.mysql.entity.TimeWind;
import com.jet.demo.pojo.BlowerDataPojo;
import com.jet.demo.pojo.WaterTurbineDataPojo;
import com.jet.demo.pojo.WindDataPojo;
import com.jet.demo.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 16:27.
 */
@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private IDataService dataService;

    @RequestMapping(value = "/top1000", method = RequestMethod.GET)
    @ResponseBody
    public List<TimeWind> first1000Data() {
        return dataService.getFirst1000Data();
    }

    @RequestMapping(value = "/nextOne", method = RequestMethod.GET)
    @ResponseBody
    public List<TimeWind> nextOne(int offset) {
        return dataService.getNextOne(offset);
    }


    @RequestMapping(value = "/windDataJson", method = RequestMethod.GET)
    @ResponseBody
    public WindDataPojo getWindData(String timeFrom, String toTime) {
        return dataService.getWindData(timeFrom, toTime);
    }

    @RequestMapping(value = "/turbineData", method = RequestMethod.GET)
    @ResponseBody
    public BlowerDataPojo turbineData(Integer id, Integer number) {
        return dataService.turbineData(id, number);
    }

    @RequestMapping(value = "/gearboxData", method = RequestMethod.GET)
    @ResponseBody
    public BlowerDataPojo gearboxData(Integer id, Integer number) {
        return dataService.gearboxData(id, number);
    }

    @RequestMapping(value = "/turboData", method = RequestMethod.GET)
    @ResponseBody
    public List<List<Object>> turboData(Integer type, Integer number) {
        return dataService.turboData(type, number);
    }

    @RequestMapping(value = "/waterTurbine", method = RequestMethod.GET)
    @ResponseBody
    public WaterTurbineDataPojo waterTurbine(Integer number) {
        return dataService.waterTurbine(number);
    }
}
