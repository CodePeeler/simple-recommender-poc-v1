package com.simon.recommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.simon.services.MovieService;
import com.simon.util.MergeSort;
import com.simon.util.SimilartyFunctions;
import com.simon.util.SortAlgorithm;

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
		
		/*
		 * Prepare the Data...
		 */
		
		//	Get a reference to the global movie catalog.
		Map<String, Movie> movies = MovieService.getMovieCatalog();
		
		movies.put(M1, new Movie(M1));
		movies.put(M2, new Movie(M2));
		movies.put(M3, new Movie(M3));
		movies.put(M4, new Movie(M4));
		movies.put(M5, new Movie(M5));
		movies.put(M6, new Movie(M6));
		
		List<Review> reviews;
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
		
		// Find movies that toby (a reviewer) hasn't seen yet.
		MovieService ms = new MovieService();
		Map<String, Movie> moviesToWatch = ms.findMoviesToWatch(toby);
		
		//	Tabulating data to calculate the weighted recommendation score...	IN PROGRESS!!!	
		String movieTitle ="";
		Movie movie;
		double simScore = 0;
		Double runningTotal = new Double(0);
		Map<String, Double> sumSimMovieScore = new HashMap<>();
		Map<String, Double> sumOfProduct = new HashMap<>();
		Double sumOfProductRunningTotal;
						
		for(Reviewer reviewer: reviewers){
			//	Get the similarity-score relative to Toby (a reviewer) for this reviewer.
			simScore = toby.getSimilarityRankings().get(reviewer.getName()).getSimilarityMeasure();
			
			double movieScore = 0;
			for(String key: moviesToWatch.keySet()){
				Movie movieToWatch = moviesToWatch.get(key);
				
				//	Find the movie-score that the reviewer gave this movie.
				Review r = movieToWatch.getReviewByReviewer(reviewer.getName());
				
				/*
				 * Compute the statistics i.e.
				 * (1) The sum of the reviewers similarity scores for each movie. 
				 * (2) The sum of the products (similarity score times the movie 
				 * score given by each reviewer) for each movie.
			     */
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
					
					
					double simXmovieScore = simScore * movieScore;
					
					if(sumOfProduct.get(movieTitle) == null){
						sumOfProductRunningTotal = new Double(simXmovieScore);
					}else{
						sumOfProductRunningTotal = sumOfProduct.get(movieTitle);
						sumOfProductRunningTotal += new Double(simXmovieScore);
					}
					sumOfProduct.put(movieTitle, sumOfProductRunningTotal);
				
					// Test
					//System.out.println("***** simXmovieScore = "+simXmovieScore+ " Movie : "+movieToWatch.getTitle()+" Reviewer : "+reviewer.getName());
				}				
				
			}			
		}
		// test
		/*
		for(String key: sumSimMovieScore.keySet()){
			System.out.println(key+" SimSum : "+sumSimMovieScore.get(key));
		}
		for(String key: sumOfProduct.keySet()){
			System.out.println(key+" sumOfProduct : "+sumOfProduct.get(key));
		}
		*/
		
		/*
		 * We divide each weight scores by the sum of all the similarities for  
		 * reviewers of that movie. This allows us to correct for movies that 
		 * were reviewed by more people.
		 */
		List<MovieRecommendation> rankedMovieRecommendations = new ArrayList<MovieRecommendation>();
		movie = null;
		
		System.out.println("*** RECOMMENDATIONS for "+toby.getName()+" ***");
		for(String key: moviesToWatch.keySet()){
			
			movie = moviesToWatch.get(key);
			movieTitle = movie.getTitle();
			
			Double weightedMovieScore = sumOfProduct.get(movieTitle)/sumSimMovieScore.get(movieTitle);
			rankedMovieRecommendations.add(new MovieRecommendation(movie, weightedMovieScore));						
		}		
		
		SortAlgorithm<MovieRecommendation> sortAlgorithm = new MergeSort<MovieRecommendation>();
		//sortAlgorithm.sort(ranking, (a,b) -> a.getSimilarityMeasure() > b.getSimilarityMeasure() ? 0:1 );
		sortAlgorithm.sort(rankedMovieRecommendations, MovieRecommendation::compareTo);		
		
		for(MovieRecommendation mr: rankedMovieRecommendations){
			System.out.println("Movie : " +mr.getMovie().getTitle()+"Recommendation Score : "+mr.getRecommendationScore());
		}
		
		/* IN PROGRESS...
		 * 1. Clean up and optimize code. 
		 * 2. Extract to method.
		 * 3. Create MoveRecommendation object to hold, "movie", "recScore", "summarySats", "reviewer"(who the recommendation is for).
		 */
	}
	

}
