package cz2101lab1;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sorting {
	
	private int[] initialList;
	private int[] list;
	private int size = 1000;
	private int minSize = 1;
	private LinkedHashMap<Integer, Long> map = new LinkedHashMap<Integer, Long>();
	private long keyComps = 0;
	
	public Sorting() {
		Random rng = new Random();
		list = new int[size];
		initialList = new int[size];
		for (int i = 0; i < size; i++) {
			list[i] = rng.nextInt(size);
			initialList[i] = list[i];
		}
	}
	
	// from lect notes
	public void insertionSort(int n, int m) {
		for (int i = n + 1; i <= m; i++) {
			for (int j = i; j > n; j--) {
				keyComps++;
				if (list[j] < list[j - 1]) {
					int temp = list[j];
					list[j] = list[j - 1];
					list[j - 1] = temp;
				} else {
					break;
				}
			}
		}
	}
	
	public void testSortingAlgo() {
		// can change the minSize to test
		minSize = 10;
		keyComps = 0;
		
		System.out.print("Initial List: ");
		print();
		System.out.println();
		mergeSort(0, size - 1);
		System.out.print("Sorted List: ");
		print();
		System.out.println();
		
		checkIfSorted();
	    
		// revert list to unsorted version
		for (int j = 0; j < size; j++) {
			list[j] = initialList[j];
		}
	}
	
	// changes minSize and performs sorting algo on different minSize values
	// prints out number of key comparisons taken for each minSize
	// can change to time taken for algorithm to run for each minSize too, uncomment lines 68, 70, 71, 72 and comment out 73
	public void mixedSort() {
		for (int i = 1; i < size; i += 1) {
			keyComps = 0;
			minSize = i;
			long before = System.nanoTime();
			mergeSort(0, size - 1);
			long after = System.nanoTime();
			long timeTaken = after - before;
			map.put(minSize, timeTaken);
//			map.put(minSize, keyComps);
			for (int j = 0; j < size; j++) {
				list[j] = initialList[j];
			}
		}
		Stream<Map.Entry<Integer, Long>> sorted =
			    map.entrySet().stream()
			       .sorted(Map.Entry.comparingByValue());
		sorted.forEach(System.out::println);
		printResultsIntoList();
	}
	
	private void printResultsIntoList() {	
		String minSize = map.keySet().stream().map(entrySet -> String.valueOf(entrySet)).collect(Collectors.joining(",", "[", "]"));
		String values = map.values().stream().map(entrySet -> String.valueOf(entrySet)).collect(Collectors.joining(",", "[", "]"));
		System.out.println(minSize);
		System.out.println(values);
	}
	
	// from lect
	private void mergeSort(int n, int m) {
		int mid = (n + m) / 2;
		if (m - n + 1 <= minSize) {
			insertionSort(n, m);
		} else {
			mergeSort(n, mid);
			mergeSort(mid + 1, m);
			merge(n, m);
		}
	}
	
	// from lect
	private void merge(int n, int m) {
		if (m - n <= 0) return;
		int mid = (n + m) / 2;
		int a = n;
		int b = mid + 1;
		while (a <= mid && b <= m) {
			keyComps++;
			if (list[a] < list[b]) {
				a++;
			} else if (list[b] < list[a]) {
				int temp = list[b];
				for (int i = b; i > a; i--) {
					list[i] = list[i - 1];
				}
				list[a] = temp;
				a++;
				mid++;
				b++;
			} else {
				if (a == mid && b == n) {
					break;
				}
				for (int i = b; i > a; i--) {
					list[i] = list[i - 1];
				}
				a += 2;
				mid++;
				b++;
			}
		}
	}
	
	private void print() {
		for (int i = 0; i < size; i++) {
			System.out.print(list[i] + " ");
		}
	}
	
	private void checkIfSorted() {
		boolean notSorted = false;
		for (int i = 0; i < list.length - 1; i++) {
	        if (list[i] > list[i + 1]) {
	            notSorted = true;
	        	break;
	        }
	    }
		if (notSorted) {
			System.out.println("NOT SORTED");
		} else {
			System.out.println("SORTED");
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sorting sorting1 = new Sorting();
		sorting1.checkIfSorted();
		System.out.println("Testing sorting algorithm: ");
		sorting1.testSortingAlgo();
		
		System.out.println();
		System.out.println("Print out number of key comparisons: ");
		sorting1.mixedSort();
	}

}
