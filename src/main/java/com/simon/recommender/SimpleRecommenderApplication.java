package com.simon.recommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.simon.util.SimilartyFunctions;

public class SimpleRecommenderApplication {
	
	/*
	 * In a real application this data would come
	 * from a db or a data service.
	 */
	private static final String M1 = "Lady in the Water";
	private static final String M2 = "Snakes on a Plane";
	private static final String M3 = "Just My Luck";
	private static final String M4 = "Superman Returns";
	private static final String M5 = "You, Me and Dupree";
	private static final String M6 = "The Night Listener";
	
	public static void main(String[] args){
		
		//Prepare the Data...
		List<Review> reviews;
		
		Map<String, Movie> movies = new HashMap<>();
		movies.put(M1, new Movie(M1));
		movies.put(M2, new Movie(M2));
		movies.put(M3, new Movie(M3));
		movies.put(M4, new Movie(M4));
		movies.put(M5, new Movie(M5));
		movies.put(M6, new Movie(M6));		
		
		Reviewer lisa = new Reviewer("Lisa Rose");		
		reviews = Arrays.asList(
			new Review(lisa, movies.get(M1), 2.5),
			new Review(lisa, movies.get(M2), 3.5),
			new Review(lisa, movies.get(M3), 3.0),
			new Review(lisa, movies.get(M4), 3.5),
			new Review(lisa, movies.get(M5), 2.5),
			new Review(lisa, movies.get(M6), 3.0)
		);
		lisa.addReviews(reviews);
		lisa.postReviewsToMovies(reviews);
		
		Reviewer gene = new Reviewer("Gene Seymour");
		reviews = Arrays.asList(
			new Review(gene, movies.get(M1), 3.0),
			new Review(gene, movies.get(M2), 3.5),
			new Review(gene, movies.get(M3), 1.5),
			new Review(gene, movies.get(M4), 5.0),
			new Review(gene, movies.get(M5), 3.5),
			new Review(gene, movies.get(M6), 3.0)
		);
		gene.addReviews(reviews);
		gene.postReviewsToMovies(reviews);
		
		
		Reviewer michael = new Reviewer("Michael Phillips");
		reviews = Arrays.asList(
			new Review(michael, movies.get(M1), 2.5),
			new Review(michael, movies.get(M2), 3.0),			
			new Review(michael, movies.get(M4), 3.5),
			new Review(michael, movies.get(M6), 4.0)
			
		);
		michael.addReviews(reviews);
		michael.postReviewsToMovies(reviews);
		
		
		Reviewer claudia = new Reviewer("Claudia Puig");
			reviews = Arrays.asList(
				new Review(claudia, movies.get(M2), 3.5),			
				new Review(claudia, movies.get(M3), 3.0),
				new Review(claudia, movies.get(M4), 4.0),				
				new Review(claudia, movies.get(M5), 2.5),
				new Review(claudia, movies.get(M6), 4.5)				
			);
		claudia.addReviews(reviews);
		claudia.postReviewsToMovies(reviews);
		
		
		Reviewer mick = new Reviewer("Mick LaSalle");
		reviews = Arrays.asList(
			new Review(mick, movies.get(M1), 3.0),
			new Review(mick, movies.get(M2), 4.0),
			new Review(mick, movies.get(M3), 2.0),
			new Review(mick, movies.get(M4), 3.0),
			new Review(mick, movies.get(M5), 2.0),
			new Review(mick, movies.get(M6), 3.0)
		);
		mick.addReviews(reviews);
		mick.postReviewsToMovies(reviews);

		
		Reviewer jack = new Reviewer("Jack Matthews");		
		reviews = Arrays.asList(
			new Review(jack, movies.get(M1), 3.0),
			new Review(jack, movies.get(M2), 4.0),
			new Review(jack, movies.get(M4), 5.0),
			new Review(jack, movies.get(M5), 3.5),
			new Review(jack, movies.get(M6), 3.0)
		);
		jack.addReviews(reviews);
		jack.postReviewsToMovies(reviews);
		
		
		Reviewer toby = new Reviewer("Toby");		
		reviews = Arrays.asList(
			new Review(toby, movies.get(M2), 4.5),
			new Review(toby, movies.get(M4), 4.0),
			new Review(toby, movies.get(M5), 1.0)
		);
		toby.addReviews(reviews);
		toby.postReviewsToMovies(reviews);			

		// Add all the reviewers except Toby to a list...
		
		List<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(lisa);
		reviewers.add(gene);		
		//reviewers.add(michael);
		reviewers.add(claudia);		
		reviewers.add(mick);
		reviewers.add(jack);
		
		// Calculate the ranking for each reviewer relative to Toby.
		
		toby.calculateSimilarityRankings(reviewers, SimilartyFunctions::calculatePearsonsCoefficient);
		
		System.out.println("*** Getting reviewer's similar to "+toby.getName()+" ***\n");		

		//	Print out the raw rankings
		Rank rank;
		for(String key: toby.getSimilarityRankings().keySet() ){
			rank = toby.getSimilarityRankings().get(key);
			System.out.println("Reviewer : "+rank.getReviewer().getName()+"\nSimilarity measure : "+rank.getSimilarityMeasure()+"\n");
		}
		
		//Find the movies I haven't seen yet but have been reviewed - THERE IS A BETTER WAY TO DO THIS... I WILL FIX IT LATER!
		Map<String, Movie> moviesToWatch = new HashMap<>();
		String movieTitle;
		Movie movie;
		boolean matchedMovie = false;
		
		for(String key : movies.keySet()){
			movieTitle = movies.get(key).getTitle();
			movie = movies.get(key);
			
			for(Review myMovies : toby.getReviews()){				
				
				if( movieTitle.equals(myMovies.getMovie().getTitle()) ){
					matchedMovie = true;
					break;
				}
			}
			if(matchedMovie == false){
				moviesToWatch.put(movieTitle, movie);
			}
			matchedMovie = false;			
		}

		//Quick visual check!
		for(String key: moviesToWatch.keySet())
			System.out.println(moviesToWatch.get(key).getTitle());
		
		//	Tabulating data to calculate the weighted recommendation score...	IN PROGRESS!!!	
		double simScore = 0;
		Double runningTotal = new Double(0);
		Map<String, Double> sumSimMovieScore = new HashMap<>();
						
		for(Reviewer reviewer: reviewers){
			//	Get the similarity-score relative to Toby (a reviewer) for this reviewer.
			simScore = toby.getSimilarityRankings().get(reviewer.getName()).getSimilarityMeasure();
			
			double movieScore = 0;
			for(String key: moviesToWatch.keySet()){
				Movie movieToWatch = moviesToWatch.get(key);
				
				//Find the movie-score that the reviewer gave this movie.
				Review r = movieToWatch.getReviewByReviewer(reviewer.getName());
				if( r != null){
					movieScore = movieToWatch.getReviewByReviewer(reviewer.getName()).getScore();					
					movieTitle = movieToWatch.getTitle();
					
					if(sumSimMovieScore.get(movieTitle) == null) {
						runningTotal = new Double(simScore);
					}else{
						runningTotal = new Double( sumSimMovieScore.get(movieTitle));
						runningTotal += new Double(simScore);
					}					
					sumSimMovieScore.put(movieTitle, runningTotal);
								
					//	Multiple by the similarity score by the movie score.
					double simXmovieScore = simScore * movieScore;
				
					// Test
					System.out.println("***** simXmovieScore = "+simXmovieScore+ " Movie : "+movieToWatch.getTitle()+" Reviewer : "+reviewer.getName());
				}				
				
			}			
		}
		// test
		for(String key: sumSimMovieScore.keySet()){
			System.out.println(key+" SimSum : "+sumSimMovieScore.get(key));
		}
	}

}
