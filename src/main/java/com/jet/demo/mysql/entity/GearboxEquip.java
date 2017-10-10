package com.jet.demo.mysql.entity;

import javax.persistence.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/9 9:06.
 */
@Entity
@Table(name = "gearbox_equip")
public class GearboxEquip {


    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Id
    @Column(name = "equip_name")
    private String equipName;

    @Column(name = "ucl")
    private Double ucl;

    @Column(name = "x")
    private Double x;

    @Column(name = "lcl")
    private Double lcl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Double getUcl() {
        return ucl;
    }

    public void setUcl(Double ucl) {
        this.ucl = ucl;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getLcl() {
        return lcl;
    }

    public void setLcl(Double lcl) {
        this.lcl = lcl;
    }
}
