package com.jet.demo.pojo;

import com.jet.demo.mysql.entity.WindData;
import com.jet.demo.mysql.entity.WindResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/19 15:24.
 */
public class WindDataPojo {
    private List<WindResult> resultData = new ArrayList<>();

    private List<WindData> normalData = new ArrayList<>();

    private List<WindData> exceptionData = new ArrayList<>();

    public WindDataPojo() {
    }

    public WindDataPojo(List<WindResult> resultData, List<WindData> normalData, List<WindData> exceptionData) {
        this.resultData = resultData;
        this.normalData = normalData;
        this.exceptionData = exceptionData;
    }

    public List<WindResult> getResultData() {
        return resultData;
    }

    public void setResultData(List<WindResult> resultData) {
        this.resultData = resultData;
    }

    public List<WindData> getNormalData() {
        return normalData;
    }

    public void setNormalData(List<WindData> normalData) {
        this.normalData = normalData;
    }

    public List<WindData> getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(List<WindData> exceptionData) {
        this.exceptionData = exceptionData;
    }
}