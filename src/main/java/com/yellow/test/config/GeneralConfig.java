package com.yellow.test.config;

import com.yellow.test.props.GeneralProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class GeneralConfig {

    @Autowired
    private GeneralProperties properties;

    @PostConstruct
    public void postConstruct() {
        TimeZone.setDefault(TimeZone.getTimeZone(properties.getTimeZone()));
    }

}
