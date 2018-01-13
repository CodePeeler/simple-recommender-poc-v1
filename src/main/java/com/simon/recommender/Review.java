package com.simon.recommender;

public class Review {
	
	private Reviewer reviewer;
	private Movie movie;
	private double score;	

	public Review(Reviewer reviewer, Movie movie, double score) {
		this.reviewer = reviewer;
		this.movie = movie;
		this.score = score;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public Movie getMovie() {
		return movie;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
