package com.lian.mycollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyMultiThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMultiThreadApplication.class, args);
    }

}
