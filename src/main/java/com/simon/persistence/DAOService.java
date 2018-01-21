package com.simon.persistence;

import com.simon.recommender.Movie;

public interface DAOService {
	
	public void retrieveMovieCatalog();
	public void persistMovieCatalog(Movie movie);

}
