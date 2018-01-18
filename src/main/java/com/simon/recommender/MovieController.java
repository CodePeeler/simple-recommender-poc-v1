package com.simon.recommender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simon.dom.MovieCatalog;
import com.simon.persistence.DAOService;

/**
 * @author 
 *
 */
@RestController
public class MovieController {

	private final String SQL_QUERY_01 = "SELECT * from Movie";	
	protected Logger logger = Logger.getLogger(MovieController.class.getName());	
	
	@Autowired
	private Connection simpleConn;
	
	@Autowired
	private DAOService daoService;
		
	@RequestMapping("/movies")
	public Map<String, Movie> movies() {
		String title = "NO RESULTS";
	    String reviewers = "NO REVIEWERS";
	    int numOfReviews = 0;		
				
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
	    
	    MovieCatalog catalog = new MovieCatalog();		
		daoService.retrieveMovieCatalog(); //daoService uses JDBC Connection Pool.
		return catalog.getMovieCatalog();
	}	
	
}
