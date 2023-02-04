package com.jhhaanstra.tesseractserver.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @GetMapping(value = "/status")
    public String hello() {
        return "<pre>running</pre>";
    }

}
