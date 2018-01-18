package com.simon.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reviewer {
	
	private String name;
	private List<Review> reviews;							//	Reviews of movie's by the by this reviewer.
	private Map<String, Movie> reviewedMovies;
	private Map<String, Rank> similarityRankings; 			//	How similar other reviewers are to this reviewer.
	//private List<Movie> moviesToWatch;					//	Movies I haven't seen yet and have been reviewed.
	private List<MovieRecommendation> recommendedMovies;	// 	Recommended movies for this reviewer.
	
	
	public Reviewer(String name){
		this.name = name;
		this.reviews = new ArrayList<Review>();
		this.reviewedMovies = new HashMap<String, Movie>();
	}
	
	public Reviewer(String name, List<Review> reviews){
		this.name = name;
		this.reviews = reviews;
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
			this.reviewedMovies.put(review.getMovie().getTitle(), review.getMovie());
			this.reviews.add(review);			
		}
	}
	
	public void addReview(Review review){
		this.reviewedMovies.put(review.getMovie().getTitle(), review.getMovie());
		this.reviews.add(review);		
	}
	
	public void postReviewsToMovies(List<Review> reviews){
		for(Review review: reviews){			
			review.getMovie().addReview(review);
		}		
	}
	
	public void postReviewToMovie(Review review ){
		review.getMovie().addReview(review);		
	}	
	
	public Map<String, Movie> getReviewedMovies() {
		return reviewedMovies;
	}	

	public Map<String, Rank> getSimilarityRankings() {
		return similarityRankings;
	}

	public void setSimilarityRankings(Map<String, Rank> similarityRankings) {
		this.similarityRankings = similarityRankings;
	}
	
	public List<MovieRecommendation> getRecommendedMovies() {
		return recommendedMovies;
	}

	public void setRecommendedMovies(List<MovieRecommendation> recommendedMovies) {
		this.recommendedMovies = recommendedMovies;
	}

/*	
	public List<Movie> getMoviesToWatch() {
		return moviesToWatch;
	}

	public void setMoviesToWatch(List<Movie> moviesToWatch) {
		this.moviesToWatch = moviesToWatch;
	}
	
	public void addMovieToWatch(Movie movie){
		moviesToWatch.add(movie);
	}
*/
	public void calculateSimilarityRankings(List<Reviewer> reviewers, SimilarityMeasure sim){
		
		double similarityMetric = 0;
		Map<String, Rank> ranking = new HashMap<>();
		
		for(Reviewer reviewer: reviewers){			
			similarityMetric = sim.calculate(this.getReviews(), reviewer.getReviews());
			ranking.put(reviewer.getName(), new Rank(reviewer, similarityMetric));			
		}		
		this.setSimilarityRankings(ranking);		
	}
	
}
