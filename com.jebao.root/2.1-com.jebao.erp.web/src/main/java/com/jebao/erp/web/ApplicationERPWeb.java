package com.jebao.erp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2016/10/21.
 */
@SpringBootApplication
@ComponentScan("com.jebao.erp.web,com.jebao.erp.service.inf,com.jebao.erp.service.impl,com.jebao.jebaodb.dao")
public class ApplicationERPWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationERPWeb.class, args);
    }
}
