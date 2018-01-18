package com.simon.dom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.simon.recommender.Movie;


public class MovieCatalog {
	
	private static Map<String, Movie> catalog = new HashMap<>();	
	
	public Map<String, Movie> getMovieCatalog(){
		return catalog;
	}
	
	public void addMovieToCatalog(Movie movie) {
		// HashMap is thread-safe.
		catalog.put(movie.getTitle() , movie);		
	}
	
	/*
	 * Maybe this action should be performed by a service!
	 */
	public void addMoviesToCatalog(Map<String, Movie> movies) {
		Movie movie = null;
		for(Entry<String, Movie> entry : movies.entrySet()){
			movie = entry.getValue();
			catalog.put(movie.getTitle(), movie);
		}
	}
	
	public Movie getMovieFromCatalog(String movieTitle){
		return catalog.get(movieTitle);
	}
	
	public void removeMovieFromCatalog(String movieTitle){
		catalog.remove(movieTitle);		
	}
	
	public boolean isInCatalog(String movieTitle){
		return catalog.get(movieTitle) != null ? true : false;
	}
	
	public int getNumOfmoviesInCatalog() {
		return catalog.size();
	}

}
