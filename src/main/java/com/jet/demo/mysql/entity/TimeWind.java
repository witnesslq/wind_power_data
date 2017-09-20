package com.jet.demo.mysql.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 13:37.
 */
@Entity
@Table(name = "label_traindata")
public class TimeWind {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private Date time;

    @Column(name = "wind_speed")
    private float windSpeed;

    private float power;

    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
