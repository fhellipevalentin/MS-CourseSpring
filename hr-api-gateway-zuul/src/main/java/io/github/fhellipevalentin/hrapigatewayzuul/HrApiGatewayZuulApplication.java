package io.github.fhellipevalentin.hrapigatewayzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HrApiGatewayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrApiGatewayZuulApplication.class, args);
    }

}
