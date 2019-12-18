package edu.mum.sonet;

import edu.mum.sonet.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SonetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonetApplication.class, args);
	}

}
