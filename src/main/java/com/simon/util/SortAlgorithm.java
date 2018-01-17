package com.simon.util;

import java.util.*;

public interface SortAlgorithm<T> {
	public void sort(List<T> t, Compare<T> c);

}