package com.jet.demo.mysql.entity;

import javax.persistence.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/26 10:15.
 */
@Entity
@Table(name = "wind_data_factory")
public class WindDataFactory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "wind_speed", nullable = false)
    private float windSpeed;

    @Column(name = "power", nullable = false)
    private float power;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
