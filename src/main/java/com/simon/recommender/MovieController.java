package com.simon.recommender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;
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
	Connection simpleDAO;
	
	@Autowired
	BasicDataSource dataSourcePool;
		
	@RequestMapping("/movies")
	public Movie movies() throws SQLException {		
		
	/*** Example using simpleDAO ***/
		
		Statement stmt = null;
	    ResultSet rs = null;
		
	    stmt = simpleDAO.createStatement();
	    rs = stmt.executeQuery(SQL_QUERY_01);

	    String title = "NO RESULTS";
	    int numOfReviews = 0;
	    String reviewers = "NO REVIEWERS";
	    if (rs.next()) {
	    	title = rs.getString(1);
	    	reviewers = rs.getString(2);
	    	numOfReviews = rs.getInt(3);	    	
	    }
	    System.out.println("\nMovie : "+title+"\n Reviewers : "+reviewers+"\n Number of reviews : "+numOfReviews);
	    Movie movie = new Movie(title);
	   
    /*** Example using Connection Pool ***/
	    
	    Connection conn = null;
	    PreparedStatement pStmt = null;
	    try {
		    conn = dataSourcePool.getConnection();
		    pStmt = conn.prepareStatement(SQL_QUERY_02);
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
	    // do something...not sure what, though!
	    	System.out.println("Looks like we have a problem "+e.getMessage());
	    }
      
		return movie;
	}	
	
}
