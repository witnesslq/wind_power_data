package com.jet.demo.mysql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/9/29 11:50.
 */
@Component
public class JdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(){
        return jdbcTemplate.queryForObject("select count(id) from wind_data_factory",Integer.class);
    }
}
