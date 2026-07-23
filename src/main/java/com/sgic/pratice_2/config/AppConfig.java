//package com.sgic.pratice_2.config;
//
//import com.sgic.pratice_2.entity.Demo;
//import com.sgic.pratice_2.entity.Student;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    @Qualifier("bean1")
//    public Demo firstBean(){
//        return new Demo("Hello");
//    }
//
//    @Bean
//    @Primary
//    public Demo secondBean(){
//        return new Demo("Hello1");
//    }
//}
