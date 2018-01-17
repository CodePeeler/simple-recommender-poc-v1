package com.simon.recommender;

public class MovieRecommendation {
	
	private Movie movie;
	private double recommendationScore;	
	
	public MovieRecommendation(Movie movie, double recommendationScore) {
		super();
		this.movie = movie;
		this.recommendationScore = recommendationScore;
	}
	
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public double getRecommendationScore() {
		return recommendationScore;
	}
	public void setRecommendationScore(double recommendationScore) {
		this.recommendationScore = recommendationScore;
	}	
	public int compareTo(MovieRecommendation thatRecommendation) {		
		double thatScore = thatRecommendation.getRecommendationScore();		
		if( this.recommendationScore < thatScore ){
			return 1;
		}else{
			return 0;
		}		
	}	
}
