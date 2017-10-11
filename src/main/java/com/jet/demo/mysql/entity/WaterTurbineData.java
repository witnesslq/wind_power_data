package com.jet.demo.mysql.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/11 11:37.
 */
@Entity
@Table(name = "water_turbine_data")
public class WaterTurbineData {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "x1")
    private Double x1;

    @Column(name = "x2")
    private Double x2;

    @Column(name = "x3")
    private Double x3;

    @Column(name = "y1")
    private Double y1;

    @Column(name = "y2")
    private Double y2;

    @Column(name = "y3")
    private Double y3;

    @Column(name = "fault_type")
    private String faultType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getX3() {
        return x3;
    }

    public void setX3(Double x3) {
        this.x3 = x3;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    public Double getY3() {
        return y3;
    }

    public void setY3(Double y3) {
        this.y3 = y3;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }
}
