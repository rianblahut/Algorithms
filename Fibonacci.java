import java.util.*;
public class Fibonacci {
	
	public static long fibRecursive(int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		else return fibRecursive(n-1) + fibRecursive(n-2);
	}
	
	public static long fibIterative(int n) {
		long cache[] = new long[n+1];
		if (n == 0) return 0;
		if (n >= 1) {
			cache[0] = 0;
			cache[1] = 1;
			for (int i = 2; i < n+1; i++) {
				cache[i] = cache[i-1] + cache[i-2];
			}
			printLongArray(cache);
			return cache[n];
		}
		return -1;
	}
	public static void printLongArray(long array[]) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(i + ": " + array[i]);
		}
	}
	
	public static void main (String[] args) {
		
		int n = 5;
		
		System.out.println(n + "th Fibonacci (Recursive): " + fibRecursive(n));
		System.out.println(n + "th Fibonacci (Iterative): " + fibIterative(n));
		
	}

}
