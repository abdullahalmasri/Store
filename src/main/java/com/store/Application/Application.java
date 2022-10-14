package com.store.Application;

import com.store.Application.configuration.Oauth2oConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Oauth2oConfig appConfiguration = context.getBean(Oauth2oConfig.class);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
