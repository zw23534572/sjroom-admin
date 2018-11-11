package com.github.sjroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations={"classpath:application-bean.xml"})
public class StartApplication  {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
