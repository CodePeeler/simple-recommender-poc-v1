package com.simon.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simon.dom.MovieCatalog;
import com.simon.recommender.Movie;

/*
 * This class maps DB tables to Objects in DOM. 
 */
@Service("daoServiceMySQL")
public class DAOServiceMySQL implements DAOService {
	
	private static final String SQL_RETRIEVE_MOVIES = "SELECT * FROM movie_catalog";
	//private final String SQL_PERSIST_MOVIES = "";
	
	@Autowired
	private BasicDataSource dsPooled;	
	
	@Override
	public void retrieveMovieCatalog(){		
	    try {	
	    	Connection conn = dsPooled.getConnection();
	    	PreparedStatement pStmt = null;
	    	
		    pStmt = conn.prepareStatement(SQL_RETRIEVE_MOVIES);
		    ResultSet resultSet = pStmt.executeQuery();
		    MovieCatalog movieCatalog = new MovieCatalog();
		    
		    String movieTitle = null;
		    while (resultSet.next()){
		    	{ 
		    		movieTitle = resultSet.getString("movie_title");
		    		movieCatalog.addMovieToCatalog(new Movie(movieTitle));				    
		    	}
	    	}		    
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	  		
	}

/*
	public boolean persistMovieCatalog(){
		return false;		
	}
*/

}
