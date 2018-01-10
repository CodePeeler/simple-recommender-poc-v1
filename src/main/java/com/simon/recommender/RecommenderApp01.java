package com.simon.recommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecommenderApp01 {
	
	public static void main(String[] args){
		
		//Prepare the Data...
		
		Reviewer lisa = new Reviewer("Lisa Rose");
		
		lisa.getReviewList().addAll(
			Arrays.asList(
				new MovieScore("Lady in the Water", 2.5),
				new MovieScore("Snakes on a Plane", 3.5),
				new MovieScore("Just My Luck", 3.0),
				new MovieScore("Superman Returns", 3.5),
				new MovieScore("You, Me and Dupree", 2.5),
				new MovieScore("The Night Listener", 3.0)
			)
		);		

		Reviewer gene = new Reviewer("Gene Seymour");

		gene.getReviewList().addAll(
			Arrays.asList(
				new MovieScore("Lady in the Water", 3.0),
				new MovieScore("Snakes on a Plane", 3.5),
				new MovieScore("Just My Luck", 1.5),
				new MovieScore("Superman Returns", 5.0),
				new MovieScore("You, Me and Dupree", 3.5),
				new MovieScore("The Night Listener", 3.0)
			)
		);
		
		Reviewer mick = new Reviewer("Mick LaSalle");

		mick.getReviewList().addAll(
			Arrays.asList(
				new MovieScore("Lady in the Water", 3.0),
				new MovieScore("Snakes on a Plane", 4.0),
				new MovieScore("Just My Luck", 2.0),
				new MovieScore("Superman Returns", 3.0),
				new MovieScore("You, Me and Dupree", 2.0),
				new MovieScore("The Night Listener", 3.0)
			)
		);
		
		Reviewer toby = new Reviewer("Toby");
		
		toby.getReviewList().addAll(
			Arrays.asList(
				new MovieScore("Snakes on a Plane", 4.5),
				new MovieScore("Superman Returns", 4.0),
				new MovieScore("You, Me and Dupree", 1.0)					
			)
		);
		
		
		// Add the reviewers...
		
		List<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(lisa);
		reviewers.add(gene);
		reviewers.add(mick);
		
		// Calculate the ranking for each reviewer relative to Toby.
		
		List<Rank> ranking = rankReviewrs(toby, reviewers);
		
		System.out.println("*** Getting recommendations for "+toby.getName()+" ***\n");
		for(Rank rank: ranking){
			System.out.println("rank.getReviewersName() : "+rank.getReviewersName()+"\nrank.getPearsonsCoefficient() : "+rank.getPearsonsCoefficient()+"\n");
		}		
		
	}

	public static List<Rank> rankReviewrs(Reviewer base_reviewer, List<Reviewer> reviewers){
		
		
		String reviewersName = null;
		double pearsonsCoefficient = 0;
		List<Rank> ranking = new ArrayList<Rank>();
		
		for(Reviewer reviewer: reviewers){
			reviewersName = reviewer.getName();
			pearsonsCoefficient = calculatePearsonsCoefficient(base_reviewer.getReviewList(), reviewer.getReviewList());
			ranking.add(new Rank(reviewersName, pearsonsCoefficient));			
		}
		
		return ranking;
	
	}
	
	//Assumes list have been sorted.
	/*
	 * Remark, this is a horrible approach... use HashMap instead.
	 */
	public static double calculatePearsonsCoefficient(List<MovieScore> r1, List<MovieScore> r2){
		
		double pearson_coefficent = 0;
		
		int n1 = r1.size();
		int n2 = r2.size();
		int n = 0;
		
		double r1Score = 0;
		double r2Score = 0;
		double sumOf_x = 0;
		double sumOf_y = 0;
		double sumOf_xSquared = 0;
		double sumOf_ySquared = 0;		
		double productOfSums = 0;
		double sumOfProducts = 0;		
		
		for(int i=0; i < n1; i++){
			
			for(int j=n; j < n2; j++){
				
				if( r1.get(i).getMovie().equals(r2.get(j).getMovie()) ){
					
					r1Score = r1.get(i).getScore();
					r2Score = r2.get(j).getScore();
					
					sumOf_x += r1Score;
					sumOf_y += r2Score;
					
					sumOf_xSquared += Math.pow(r1Score, 2);
					sumOf_ySquared += Math.pow(r2Score, 2);
					
					sumOfProducts += (r1Score * r2Score);
					n++;
					break;
				}				
			}			
		}
		
		if( n != 0) {
			productOfSums = sumOf_x * sumOf_y;
			double pearsons_numerator = n*(sumOfProducts) - (productOfSums);
			double pearsons_denominator = ( Math.sqrt(n*sumOf_xSquared - Math.pow(sumOf_x, 2) ) ) * ( Math.sqrt(n*sumOf_ySquared - Math.pow(sumOf_y, 2) ) );
			pearson_coefficent = pearsons_numerator / pearsons_denominator;
		}		
		return  pearson_coefficent;
	}
	
}
