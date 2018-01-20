package com.simon.recommender;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.simon.persistence", "com.simon.services", "com.simon.util"})
public class RecommenderConfig {

}
