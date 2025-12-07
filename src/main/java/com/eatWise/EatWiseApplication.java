package com.eatWise;

import com.eatWise.client.RagClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = RagClient.class)
public class EatWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatWiseApplication.class, args);
	}

}
