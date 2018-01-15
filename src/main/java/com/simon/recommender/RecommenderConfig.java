package com.simon.recommender;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.simon.persistence"})
public class RecommenderConfig {

}
