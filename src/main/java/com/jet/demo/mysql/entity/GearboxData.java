package com.jet.demo.mysql.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 8:59.
 */
@Entity
@Table(name = "gearbox_data")
public class GearboxData implements Serializable {

    private static final long serialVersionUID = 5837600992592585017L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "equip_name")
    private String equipName;

    @Column(name = "value")
    private Double value;

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

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
