package com.simon.util;

import java.util.ArrayList;
import java.util.List;

//FYI, O(n*log( n )). Java's Collection.sort() is an implementation of MergeSort.

public class MergeSort<T> implements SortAlgorithm<T>  {
	
	@Override
	public void sort(List<T> array, Compare<T> c) {
		mergeSort(array, 0, array.size()-1, c);

	}

	private void mergeSort(List<T> array, int p, int r, Compare<T> c){
		if ( p < r ) { 
			int q = (p+r)/2; //java truncation = floor.
			mergeSort(array, p, q, c);
			mergeSort(array, q+1, r, c);
			merge(array, p, q, r, c);
		}
	}

	private void merge(List<T> array, int p, int q, int r, Compare<T> com){	  
		List<T> array_1 = new ArrayList<T>();
		List<T> array_2 = new ArrayList<T>();

		int c = p;

		for(int i=0; i<q-p+1; i++){
			array_1.add(i, array.get(c));			  
			c++;
		}

		int t=q+1;	 
		for(int j=0; j<r-q; j++){	    
			array_2.add(j, array.get(t));		  
			t++;
		}

		int k=p, i=0;
		for( int j=0; k<r+1 && j< r-q && i < (q-p+1); k++){	    
			if(com.compare(array_1.get(i), array_2.get(j)) <= 0 ){			 
				array.set(k,  array_1.get(i));
				i++;
			}else{	      
				array.set(k,  array_2.get(j));
				j++;
			}
		}
		for( ; i < array_1.size(); k++ ){	    
			array.set(k,  array_1.get(i));
			i++;
		}
	}
	
}
