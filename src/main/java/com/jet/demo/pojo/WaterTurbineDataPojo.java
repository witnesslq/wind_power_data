package com.jet.demo.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/11 11:52.
 */
public class WaterTurbineDataPojo {

    private List<List<Object>> x1Line = new ArrayList<>();
    private List<List<Object>> x2Line = new ArrayList<>();
    private List<List<Object>> x3Line = new ArrayList<>();
    private List<List<Object>> y1Line = new ArrayList<>();
    private List<List<Object>> y2Line = new ArrayList<>();
    private List<List<Object>> y3Line = new ArrayList<>();

    public List<List<Object>> getX1Line() {
        return x1Line;
    }

    public void setX1Line(List<List<Object>> x1Line) {
        this.x1Line = x1Line;
    }

    public List<List<Object>> getX2Line() {
        return x2Line;
    }

    public void setX2Line(List<List<Object>> x2Line) {
        this.x2Line = x2Line;
    }

    public List<List<Object>> getX3Line() {
        return x3Line;
    }

    public void setX3Line(List<List<Object>> x3Line) {
        this.x3Line = x3Line;
    }

    public List<List<Object>> getY1Line() {
        return y1Line;
    }

    public void setY1Line(List<List<Object>> y1Line) {
        this.y1Line = y1Line;
    }

    public List<List<Object>> getY2Line() {
        return y2Line;
    }

    public void setY2Line(List<List<Object>> y2Line) {
        this.y2Line = y2Line;
    }

    public List<List<Object>> getY3Line() {
        return y3Line;
    }

    public void setY3Line(List<List<Object>> y3Line) {
        this.y3Line = y3Line;
    }
}
