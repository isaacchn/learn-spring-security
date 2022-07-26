package me.isaac.demo_one;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class OneApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OneApplication.class, args);
        log.info("111");
    }
}
