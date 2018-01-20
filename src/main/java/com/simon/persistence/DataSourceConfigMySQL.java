package com.simon.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/*
 * This class holds a number of data sources and 
 * connections for experimenting with.
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfigMySQL {
	
	@Value("${mysql.jdbcUrl}")
	private String jdbcUrl;
	
	@Value("${mysql.username}")
	private String username;
	
	@Value("${mysql.password}")
	private String password;
	
	@Value("${mysql.driver}")
	private String driver;
	
	@Value("${h2.schema_location}")
	private String schema_location;
	
	@Value("${h2.test_data_location}")
	private String test_data_location;

	/*
	 * Creates and returns a single connection. Obviously don't use in 
	 * multithreaded applications.
	 */
	@Bean
	public Connection simpleConn() {
		Connection conn = null;		
		try {
			return DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			System.out.println("Looks like we have a problem "+e.getMessage());			
			//TODO: Throw a more meaningful exception if possible.
		}
		return conn;		
	}
	/*
	 * Creates a pool of connections.
	 */
	@Primary	
	@Bean	
	public BasicDataSource dsPooled() {		
		BasicDataSource dsPooled = new BasicDataSource();
		dsPooled.setDriverClassName(driver);		
		dsPooled.setUrl(jdbcUrl);
		dsPooled.setUsername(username);
		dsPooled.setPassword(password);
		dsPooled.setInitialSize(5);
		dsPooled.setMaxActive(10);   
		return dsPooled;
	}
	
	/*****************************************
	 * Spring has three data-source classes. *
	 *****************************************/	
	/*
	 * Data source returns a new connection every time a connection is requested - not pooled.
	 */
	@Bean
	public DriverManagerDataSource springConn() {		
		DriverManagerDataSource springConn = new DriverManagerDataSource();
		springConn.setDriverClassName(driver);
		springConn.setUrl(jdbcUrl);
		springConn.setUsername(username);
		springConn.setPassword(password);		
		return springConn;
	}
	/*
	 * Works much the same as DriverManagerData-Source except that it works
	 * with the JDBC driver directly.
	 */	
	@Bean
	public SimpleDriverDataSource simpleSpringConn() {		
		SimpleDriverDataSource simpleSpringConn = new SimpleDriverDataSource();
		simpleSpringConn.setDriverClass(com.mysql.cj.jdbc.Driver.class);				
		simpleSpringConn.setUrl(jdbcUrl);
		simpleSpringConn.setUsername(username);
		simpleSpringConn.setPassword(password);		
		return simpleSpringConn;
	}
	/*
	 * Returns the same connection every time a connection is requested.
	 * Obviously don't use in multithreaded applications.
	 */
	@Bean
	public SingleConnectionDataSource singleSpringConn() {			
		SingleConnectionDataSource singleSpringConn = new SingleConnectionDataSource();
		singleSpringConn.setDriverClassName(driver);
		singleSpringConn.setUrl(jdbcUrl);
		singleSpringConn.setUsername(username);
		singleSpringConn.setPassword(password);		
		return singleSpringConn;		
	}
	/**************************************************************
	 * H2 Embedded database. For testing - database is reset	  *
	 * every time you restart your application or run your tests  *
	 **************************************************************/
	@Bean
	public Connection dataSource() {
		Connection conn = null;
		try {
			conn = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript(schema_location)
			.addScript(test_data_location)
			.build().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			//TODO: Throw a more meaningful exception if possible.
		}
		return conn;
	}
	
	/**************************************************************
	 * Java Naming and Directory Interface - is a good option     * 
	 * for production - as you can look up the correct DB to use  *	
	 **************************************************************/
/*
 * 	@Profile("production")
	@Bean
	public JndiObjectFactoryBean dataSourceJNDI() {
		JndiObjectFactoryBean jndiObject = new JndiObjectFactoryBean();
		jndiObject.setJndiName("jdbc/productionDS");
		jndiObject.setResourceRef(true);
		jndiObject.setProxyInterface(javax.sql.DataSource.class);
		return jndiObject;
	}
*/	
}
