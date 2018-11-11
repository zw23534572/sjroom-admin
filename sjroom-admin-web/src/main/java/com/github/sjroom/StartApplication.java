package com.github.sjroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations={"classpath:application-bean.xml"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class StartApplication  {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
