package com.simon.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reviewer {
	
	private String name;
	private List<Review> reviews;					//	Reviews of movie's by the by this reviewer.
	private Map<String, Rank> similarityRankings; 	//	How similar are other reviewers to this reviewer.
	private List<Movie> moviesToWatch;				//	Recommended movies for this reviewer to watch. 	
	
	public Reviewer(String name){
		this.name = name;
		this.reviews = new ArrayList<Review>();
	}
	
	public Reviewer(String name, List<Review> reviews){
		this.name = name;
		this.reviews = reviews;
	}
	
	public void addReview(Review review){
		reviews.add(review);		
	}
	
	public void postReviewToMovie(Review review ){
		review.getMovie().addReview(review);		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void addReviews(List<Review> reviews) {		
		for(Review review: reviews){
			this.reviews.add(review);			
		}
	}
	
	public void postReviewsToMovies(List<Review> reviews){
		for(Review review: reviews){			
			review.getMovie().addReview(review);
		}		
	}

	public Map<String, Rank> getSimilarityRankings() {
		return similarityRankings;
	}

	public void setSimilarityRankings(Map<String, Rank> similarityRankings) {
		this.similarityRankings = similarityRankings;
	}	
	
	public List<Movie> getMoviesToWatch() {
		return moviesToWatch;
	}

	public void setMoviesToWatch(List<Movie> moviesToWatch) {
		this.moviesToWatch = moviesToWatch;
	}
	
	public void addMovieToWatch(Movie movie){
		moviesToWatch.add(movie);
	}

	public void calculateSimilarityRankings(List<Reviewer> reviewers, SimilarityMeasure sim){
		
		double similarityMetric = 0;
		Map<String, Rank> ranking = new HashMap<>();
		
		for(Reviewer reviewer: reviewers){			
			similarityMetric = sim.calculate(this.getReviews(), reviewer.getReviews());
			ranking.put(reviewer.getName(), new Rank(reviewer, similarityMetric));			
		}
		
		//Sort the list... highest value first.
		//SortAlgorithm<Rank> sortAlgorithm = new MergeSort<Rank>();
		//sortAlgorithm.sort(ranking, (a,b) -> a.getSimilarityMeasure() > b.getSimilarityMeasure() ? 0:1 );
		
		this.setSimilarityRankings(ranking);		
	}

}
