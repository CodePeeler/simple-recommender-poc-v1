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
	
	private final String SQL_RETRIEVE_MOVIES = "SELECT * FROM movie_catalog";
	private final String SQL_PERSIST_MOVIES = "INSERT INTO movie_catalog (movie_title, movie_details) VALUES(?, ?)";
	
	@Autowired
	private BasicDataSource dsPooled;	
	
	@Override
	public void retrieveMovieCatalog(){
		PreparedStatement pStmt = null;
		Connection conn = null;
	    try {	
	    	conn = dsPooled.getConnection();
	    	pStmt = null;
	    	
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
	    } finally {
			try {
				if (pStmt != null) {
					pStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			// I'm even less sure about what to do here
			}
		}
	  		
	}

	public void persistMovieCatalog(Movie movie){
		System.out.println("At persistMovieCatalog");
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			conn = dsPooled.getConnection();			
			pStmt = conn.prepareStatement(SQL_PERSIST_MOVIES);
			pStmt.setString(1, movie.getTitle());
			pStmt.setString(2, "");
			pStmt.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null) {
					pStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
}
