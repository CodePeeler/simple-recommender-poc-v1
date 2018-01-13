package com.simon.recommender;

public class Rank {
	
	Reviewer reviewer;
	double similarityMeasure;	
	
	public Rank(Reviewer reviewer, double similarityMeasure) {
		this.reviewer = reviewer;
		this.similarityMeasure = similarityMeasure;
	}
	
	public Reviewer getReviewer() {
		return reviewer;
	}
	
	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}
	
	public double getSimilarityMeasure() {
		return similarityMeasure;
	}
	
	public void setSimilarityMeasure(double similarityMeasure) {
		this.similarityMeasure = similarityMeasure;
	}	
}
