package com.simon.recommender;

public class MovieScore {

	private String movie;
	private double score;

	public MovieScore(String movie, double score){
		this.movie = movie;
		this.score = score;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
