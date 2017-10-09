package com.jet.demo.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 10:20.
 */
public class TurbineDataPojo {
    private List<List<Object>> values = new ArrayList<>();
    private List<List<Object>> ucl = new ArrayList<>();
    private List<List<Object>> x = new ArrayList<>();
    private List<List<Object>> lcl = new ArrayList<>();

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public List<List<Object>> getUcl() {
        return ucl;
    }

    public void setUcl(List<List<Object>> ucl) {
        this.ucl = ucl;
    }

    public List<List<Object>> getX() {
        return x;
    }

    public void setX(List<List<Object>> x) {
        this.x = x;
    }

    public List<List<Object>> getLcl() {
        return lcl;
    }

    public void setLcl(List<List<Object>> lcl) {
        this.lcl = lcl;
    }
}
