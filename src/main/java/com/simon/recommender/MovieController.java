package com.simon.recommender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 
 *
 */
@RestController
public class MovieController {
	private final String SQL_QUERY_01 = "SELECT * from Movie";
	private final String SQL_QUERY_02 = "SELECT * from customer";
	
	protected Logger logger = Logger.getLogger(MovieController.class.getName());	
	
	@Autowired
	Connection simpleConn;
	
	@Autowired
	Connection pooledConn;
		
	@RequestMapping("/movies")
	public Movie movies() {
		String title = "NO RESULTS";
	    String reviewers = "NO REVIEWERS";
	    int numOfReviews = 0;		
		Movie movie = new Movie(title);
		
	/*** Example using a single JDBC connection ***/
		
		Statement stmt = null;
	    ResultSet rs = null;
		
	    try {
			stmt = simpleConn.createStatement();
			rs = stmt.executeQuery(SQL_QUERY_01);
			
		    if (rs.next()) {
		    	title = rs.getString(1);
		    	reviewers = rs.getString(2);
		    	numOfReviews = rs.getInt(3);	    	
		    }
		    System.out.println("\nMovie : "+title
		    				  +"\nReviewers : "+reviewers
		    				  +"\nNumber of reviews : "+numOfReviews);
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}	    
	   
    /*** Example using JDBC Connection Pool ***/   
	    
	    PreparedStatement pStmt = null;
	    try {
		    
		    pStmt = pooledConn.prepareStatement(SQL_QUERY_02);
		    ResultSet resultSet = pStmt.executeQuery();
		    
		    int v1=0, v2=0, v3=0;		    
		    while (resultSet.next()){
		    	{    		
		    		v1  = resultSet.getInt(1);
				    v2 = resultSet.getInt(2);
				    v3 = resultSet.getInt(3);
				    System.out.println("v1 = "+v1+" v2 = "+v2+" v3 = "+v3);
		    	}
	    	}		    
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }      
		return movie;
	}	
	
}
