package ru.practicum.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "ru.practicum.client")
@SpringBootApplication
@ComponentScan(basePackages =
        {"ru.practicum.events",
        "ru.practicum.user",
        "ru.practicum.exceptions"})
public class RequestServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(RequestServiceApp.class);
    }
}
