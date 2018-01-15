package com.simon.recommender;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RecommenderApplication {	
	
	private static final Logger log = LoggerFactory.getLogger(RecommenderApplication.class);

	public static void main(String[] args) throws SQLException {		
		ApplicationContext applicationContext = SpringApplication.run(RecommenderApplication.class, args);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			log.info(name);
		}		
	}
}
