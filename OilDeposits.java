// Rian Blahut CSCI3101 Extra Credit Problem

package oildeposits;

import java.util.*;

public class OilDeposits {
	
	static int rows;
	static int columns;
	
	public static void dfs(boolean oilField[][], int r, int c) {
		
		oilField[r][c] = false; //sets this one to false so we don't return to it
		
		//checks each of the adjacent cells to the current one (r,c)
		if (r > 0 && c > 0 && oilField[r-1][c-1]) dfs (oilField, r-1, c-1); //checks upper left
		if (r > 0 && oilField[r-1][c]) dfs (oilField, r-1, c); //checks upper
		if (r > 0 && c < columns-1 && oilField[r-1][c+1]) dfs (oilField, r-1, c+1); //checks upper right
		if (c > 0 && oilField[r][c-1]) dfs (oilField, r, c-1); //checks left
		if (c < columns-1 && oilField[r][c+1]) dfs (oilField, r, c+1); //checks right
		if (r < rows-1 && c > 0 && oilField[r+1][c-1]) dfs (oilField, r+1, c-1); //checks lower left
		if (r < rows-1 && oilField[r+1][c]) dfs (oilField, r+1, c); //checks lower
		if (r < rows-1 && c < columns-1 && oilField[r+1][c+1]) dfs (oilField, r+1, c+1); //checks lower right
		
	}
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		while (true) {
			rows = s.nextInt();
			columns = s.nextInt();
			s.nextLine();
			if (rows == 0) break;
			
			// Reading the input into arrays
			boolean oilField[][] = new boolean[rows][columns];
			
			for (int r = 0; r < rows; r++) {
				String thisRow = s.nextLine();
				for (int c = 0; c < columns; c++) {
					if (thisRow.charAt(c) == '*') oilField[r][c] = false;
					else if (thisRow.charAt(c) == '@') {
						oilField[r][c] = true;
					}
				}
			}
			
			int deposits = 0;
			
			// Run through graph calling DFS on each unvisited oil patch
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < columns; c++) {
					if (oilField[r][c] == true) {
						deposits++;
						dfs(oilField, r, c);
					}
				}
			}
			
			System.out.println(deposits);
			
		}
		s.close();
	}

}
