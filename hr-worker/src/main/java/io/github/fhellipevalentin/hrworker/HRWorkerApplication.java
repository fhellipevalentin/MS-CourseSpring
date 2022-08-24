package io.github.fhellipevalentin.hrworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HRWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HRWorkerApplication.class, args);
    }

}
