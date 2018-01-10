package com.simon.recommender;

import java.util.ArrayList;
import java.util.List;

public class Reviewer {
	
	private String name;
	private List<MovieScore> reviewList;
	
	public Reviewer(String name){
		this.name = name;
		this.reviewList = new ArrayList<MovieScore>();
	}
	
	public Reviewer(String name, List<MovieScore> reviewList){
		this.name = name;
		this.reviewList = reviewList;
	}
	
	public void addMovie(MovieScore movie){
		reviewList.add(movie);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MovieScore> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<MovieScore> reviewList) {
		this.reviewList = reviewList;
	}

}
