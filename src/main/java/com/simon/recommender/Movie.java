package com.simon.recommender;

import java.util.HashMap;
import java.util.Map;
	
public class Movie {
	
	private String title;
	private Map<String, Review> reviews;		
	
	public Movie(String title) {
		this.title = title;
		this.reviews = new HashMap<>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public Review getReviewByReviewer(String reviewersName) {		
		return reviews.get(reviewersName);
	}
	
	public Map<String, Review> getReview() {
		return reviews;
	}
	
	public void addReview(Review review){
		this.reviews.put(review.getReviewer().getName(), review);		
	}

	public int getNumOfReviews() {
		return reviews.size();
	}
	
}
