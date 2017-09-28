package com.jet.demo.mysql.entity;

import javax.persistence.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/18 14:16.
 */
@Entity
public class WindData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float windSpeed;

    private float powerValid;

    @Transient
    private String faultCause;

    public WindData() {
    }

    public WindData(float windSpeed, float powerValid) {
        this.windSpeed = windSpeed;
        this.powerValid = powerValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPowerValid() {
        return powerValid;
    }

    public void setPowerValid(float powerValid) {
        this.powerValid = powerValid;
    }


    public String getFaultCause() {
        return faultCause;
    }

    public void setFaultCause(String faultCause) {
        this.faultCause = faultCause;
    }
}
