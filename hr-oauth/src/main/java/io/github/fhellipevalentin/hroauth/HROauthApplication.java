package io.github.fhellipevalentin.hroauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HROauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(HROauthApplication.class, args);
	}

}
