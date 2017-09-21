package com.jet.demo.mysql.entity;

import javax.persistence.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/21 13:36.
 */
@Entity
@Table(name = "wind_data_abnormal")
public class WindDataAbnormal {

    @Id
    private Long id;

    @Column(name = "wind_speed")
    private float wind_speed;

    @Column(name = "power")
    private float power;

    @Column(name = "t1")
    private float t1;

    @Column(name = "t2")
    private float t2;

    @Column(name = "reason")
    private String reason;

    @Override
    public String toString() {
        if ("叶片结冰".equals(reason)) {
            return "叶片结冰<br/>环境温度[°C]: " + t1 + "<br/>机舱内温度[°C]: " + t2;
        } else if ("齿轮箱失效".equals(reason)) {
            return "齿轮箱失效<br/>齿轮箱轴承温度[°C]: " + t1 + " <br/>齿轮箱油温[°C]: " + t2;
        } else {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getT1() {
        return t1;
    }

    public void setT1(float t1) {
        this.t1 = t1;
    }

    public float getT2() {
        return t2;
    }

    public void setT2(float t2) {
        this.t2 = t2;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
