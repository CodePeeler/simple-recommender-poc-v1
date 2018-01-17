package com.simon.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

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
	 * Creates a pool of connections and returns one of them.
	 */
	@Bean
	public Connection pooledConn() {
		Connection conn = null;	
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);		
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(5);
		ds.setMaxActive(10);		
		
	    try {
		    conn = ds.getConnection();
	    }catch(SQLException e){
	    	System.out.println("Looks like we have a problem "+e.getMessage());
	    	//TODO: Throw a more meaningful exception if possible.
	    }
		return conn;
	}
	
	/*****************************************
	 * Spring has three data-source classes. *
	 *****************************************/
	
	/*
	 * Returns a new connection every time a connection is requested - not pooled.
	 */
	@Bean
	public Connection springConn() {
		Connection conn = null;
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		
		try {
		    conn = ds.getConnection();
	    }catch(SQLException e){
	    	System.out.println("Looks like we have a problem "+e.getMessage());
	    	//TODO: Throw a more meaningful exception if possible.
	    }
		return conn;
	}
	/*
	 * Works much the same as DriverManagerData-Source except that it works
	 * with the JDBC driver directly.
	 */
	@Bean
	public Connection simpleSpringConn() {
		Connection conn = null;
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(com.mysql.cj.jdbc.Driver.class);				
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		
		try {
		    conn = ds.getConnection();
	    }catch(SQLException e){
	    	System.out.println("Looks like we have a problem "+e.getMessage());
	    	//TODO: Throw a more meaningful exception if possible.
	    }
		return conn;
	}
	/*
	 * Returns the same connection every time a connection is requested.
	 * Obviously don't use in multithreaded applications.
	 */
	@Bean
	public Connection singleSpringConn() {
		Connection conn = null;		
		SingleConnectionDataSource ds = new SingleConnectionDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
				
		try {
		    conn = ds.getConnection();
	    }catch(SQLException e){
	    	System.out.println("Looks like we have a problem "+e.getMessage());
	    	//TODO: Throw a more meaningful exception if possible.
	    }
		return conn;		
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
