package com.simon.util;

import java.util.List;
import com.simon.recommender.Review;

public class SimilartyFunctions {
	
	/*
	 * Remark, this is a horrible approach... use HashMap instead.
	 */
	public static double calculatePearsonsCoefficient(List<Review> r1, List<Review> r2){
		
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
