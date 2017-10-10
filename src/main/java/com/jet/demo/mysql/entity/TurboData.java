package com.jet.demo.mysql.entity;

import javax.persistence.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/10 15:42.
 */
@Entity
@Table(name = "turbo_data")
public class TurboData {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "t", nullable = false)
    private String t;

    @Column(name = "vortex_strip")
    private Double vortexStrip;

    @Column(name = "steady_state")
    private Double steadyState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Double getVortexStrip() {
        return vortexStrip;
    }

    public void setVortexStrip(Double vortexStrip) {
        this.vortexStrip = vortexStrip;
    }

    public Double getSteadyState() {
        return steadyState;
    }

    public void setSteadyState(Double steadyState) {
        this.steadyState = steadyState;
    }
}
