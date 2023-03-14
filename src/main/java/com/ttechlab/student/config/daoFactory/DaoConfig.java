package com.ttechlab.student.config.daoFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ttechlab.student.dao.DaoFactory;

@Configuration
public class DaoConfig {

    @Bean
    public DaoFactory daoFactory() {
        return new DaoFactory();
    }

}
