package com.jet.demo.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 10:20.
 */
public class BlowerDataPojo {
    private Double UCL;
    private Double X;
    private Double LCL;
    private List<List<Object>> valuesLine = new ArrayList<>();
    private List<List<Object>> uclLine = new ArrayList<>();
    private List<List<Object>> xLine = new ArrayList<>();
    private List<List<Object>> lclLine = new ArrayList<>();
    private List<List<Object>> errorLine = new ArrayList<>();

    public Double getUCL() {
        return UCL;
    }

    public void setUCL(Double UCL) {
        this.UCL = UCL;
    }

    public Double getX() {
        return X;
    }

    public void setX(Double x) {
        X = x;
    }

    public Double getLCL() {
        return LCL;
    }

    public void setLCL(Double LCL) {
        this.LCL = LCL;
    }

    public List<List<Object>> getValuesLine() {
        return valuesLine;
    }

    public void setValuesLine(List<List<Object>> valuesLine) {
        this.valuesLine = valuesLine;
    }

    public List<List<Object>> getUclLine() {
        return uclLine;
    }

    public void setUclLine(List<List<Object>> uclLine) {
        this.uclLine = uclLine;
    }

    public List<List<Object>> getxLine() {
        return xLine;
    }

    public void setxLine(List<List<Object>> xLine) {
        this.xLine = xLine;
    }

    public List<List<Object>> getLclLine() {
        return lclLine;
    }

    public void setLclLine(List<List<Object>> lclLine) {
        this.lclLine = lclLine;
    }

    public List<List<Object>> getErrorLine() {
        return errorLine;
    }

    public void setErrorLine(List<List<Object>> errorLine) {
        this.errorLine = errorLine;
    }
}
