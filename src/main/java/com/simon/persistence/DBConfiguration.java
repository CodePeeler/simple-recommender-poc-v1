package com.simon.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DBConfiguration {
	
	@Value("${mysql.jdbcUrl}")
	private String jdbcUrl;
	
	@Value("${mysql.username}")
	private String username;
	
	@Value("${mysql.password}")
	private String password;
	
	@Value("${mysql.driver}")
	private String driver;

	@Bean
	public Connection simpleDAO() {
		Connection conn = null;		
		try {
			return DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			// TODO 
			e.printStackTrace();
		}
		return conn;		
	}
	
	@Bean
	public BasicDataSource dataSourcePool() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);		
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(5);
		ds.setMaxActive(10);
		return ds;
	}
}
