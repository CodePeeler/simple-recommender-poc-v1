package com.simon.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.simon.recommender.Movie;
import com.simon.recommender.Reviewer;

public class MovieService {
		
	private static Map<String, Movie> movieCatalog = new HashMap<>();	
	
	public static Map<String, Movie> getMovieCatalog() {
		return movieCatalog;
	}

	public void addMovieToCatalog(Movie movie) {
		// HashMap is thread-safe.
		movieCatalog.put(movie.getTitle() , movie);		
	}
	
	public int getNumOfmoviesInCatalog() {
		return movieCatalog.size();
	}
	
	public Map<String, Movie> findMoviesToWatch(Reviewer reviewer){		
			
		Map<String, Movie> moviesToWatch = new HashMap<>();
		
		/* 
		 * Add all the movies from the movieCatalog that have not  
		 * been reviewed by the reviewer to the moviesToWatch list.
		 * 
		 */
		String catalogMovieTitle = "";
		Map<String, Movie> reviewersMovies = reviewer.getReviewedMovies();		

		// Iterate through the movie catalog.
		for (Entry<String, Movie> entry : movieCatalog.entrySet()) {		    
			catalogMovieTitle = entry.getValue().getTitle();
			
			// If a movie from the catalog is not on the reviewers list 
			// then add it as a possible movie suggestion.
			if(reviewersMovies.get(catalogMovieTitle) == null ){
				Movie movie = entry.getValue();
				moviesToWatch.put(catalogMovieTitle, movie);
			}			
		}		
		return moviesToWatch;
	}
}
