package com.simon.recommender;

public class Rank {
	
	String reviewersName;
	double pearsonsCoefficient;	
	
	public Rank(String reviewersName, double pearsonsCoefficient) {
		this.reviewersName = reviewersName;
		this.pearsonsCoefficient = pearsonsCoefficient;
	}
	
	public String getReviewersName() {
		return reviewersName;
	}
	public void setReviewersName(String reviewersName) {
		this.reviewersName = reviewersName;
	}
	public double getPearsonsCoefficient() {
		return pearsonsCoefficient;
	}
	public void setPearsonsCoefficient(double pearsonsCoefficient) {
		this.pearsonsCoefficient = pearsonsCoefficient;
	}
}
