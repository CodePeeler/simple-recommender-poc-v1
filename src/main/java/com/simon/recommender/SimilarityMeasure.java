package com.simon.recommender;

import java.util.List;

@FunctionalInterface
public interface SimilarityMeasure {
	public double calculate(List<Review> r1, List<Review> r2);
}
