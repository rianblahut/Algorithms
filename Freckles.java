package freckles;

/**
 * CSCI3101 (Algorithms) Code Project
 * Rian Blahut
 *
 */

import java.util.*;

public class Freckles {
	
	// Method for distance calc between two x,y points. Uses Pythagorean theorem.
	// Used for both Prim and Kruskal
	public static double getDistance(double x1, double y1, double x2, double y2) {
		double distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		return distance;
	}
	
	// Finding the minimum value vertex (by key value) of those not in the tree
	// Used by Prim
	public static int findMinKey(double key[], Boolean isInTree[]) {
		
		//initialize minimum values
		int numberOfFreckles = key.length;
		double minKeyFound = Double.MAX_VALUE;
		int minIndex = -1;
		
		//loops through all freckles. If it's not in the tree it's compared to the current min
		for (int thisFreckle = 0; thisFreckle < numberOfFreckles; thisFreckle++) {
			if (isInTree[thisFreckle] == false && key[thisFreckle] < minKeyFound) {
				minKeyFound = key[thisFreckle];
				minIndex = thisFreckle;
			}
		}
		return minIndex;
	}
	
	/*
	 * PRIM
	 * 
	 * Gets all distances, then loops through all edges, adding vertices to the tree by shortest connection 
	 * first, only if it's not already in the tree. Returns the total distance of tree-connected edges.
	 */
	public static double prim(double freckleList[][]) {
		
		// Initialize variables and arrays
		int numberOfFreckles = freckleList.length;
		int parent[] = new int[numberOfFreckles];
		double key[] = new double[numberOfFreckles];
		double freckleDistances[][] = new double[numberOfFreckles][numberOfFreckles];
		Boolean isInTree[] = new Boolean[numberOfFreckles];
		
		for (int i = 0; i < numberOfFreckles; i++) {
			key[i] = Double.MAX_VALUE; //sets all key values to high side
			isInTree[i] = false; //no points are in the finished tree yet
			//System.out.println("F" + i + ": key: " + key[i] + ", isInTree: " + isInTree[i]);
			
			//calculate distances between all points
			for (int j = 0; j < numberOfFreckles; j++) {
				if (j == i) {
					freckleDistances[i][j] = 0.0;
				}
				else {
					freckleDistances[i][j] = getDistance(freckleList[i][0],freckleList[i][1],freckleList[j][0],freckleList[j][1]);
				}
			}
		}

		key[0] = 0; //start point is freckle [0], the first one that came into the program
		parent[0] = -1; //start point has no parent
		
		// Main loop
		for (int i = 0; i < numberOfFreckles - 1; i++) {
			// Pulls lowest value key and adds to tree
			int currentFreckle = findMinKey(key, isInTree);
			isInTree[currentFreckle] = true; //sets that freckle as inside the tree
			
			for (int j = 0; j < numberOfFreckles; j++) {
				//conditions: it isn't itself (distance of 0), it isn't in the tree yet, 
				//and the distance to that point is less than the last reported distance (key value)
				if (j != currentFreckle && isInTree[j] == false && freckleDistances[currentFreckle][j] < key[j]) {
					parent[j] = currentFreckle;
					key[j] = freckleDistances[currentFreckle][j];
					//this freckle is added to the tree
				}
			}
		}
		
		// Calculate total distance (loops through all, from parent to child)
		double distance = 0;
		for (int i = 1; i < numberOfFreckles; i++) {
			distance += freckleDistances[i][parent[i]]; //adds distance from this freckle to it's parent
		}
		return distance;
	}
	
	// Finds an element in an array (recursively) using the parent array
	// Used by Kruskal
	public static int find(int i, int parent[]) {
		if (i == parent[i]) return i;
		else return find(parent[i], parent);
	}
	// Combines two sets by rank
	// Used by Kruskal
	public static void union(int i, int j, int rank[], int parent[]) {
		int iRoot = find(i, parent);
		int jRoot = find(j, parent);
		
		if (iRoot == jRoot) return; //items are already in the same set
		
		//attach the smaller root section under the larger root section
		if (rank[iRoot] > rank[jRoot]) parent[jRoot] = iRoot;
		else if (rank[iRoot] < rank[jRoot]) parent[iRoot] = jRoot;
		else {
			parent[iRoot] = jRoot;
			rank[jRoot]++;
		}
	}
	
	/*
	 * KRUSKAL
	 * 
	 * Finds total distance of all edges. Adds edges to the forest from shortest 
	 * to largest, until every vertex is connected to one large tree. Avoids cycles.
	 */
	public static double kruskal(double freckleList[][]) {
		
		// Initialize variables/arrays
		int numberOfFreckles = freckleList.length;
		if (numberOfFreckles == 1) return 0;
		double freckleDistances[][] = new double[numberOfFreckles][numberOfFreckles];
		int parent[] = new int[numberOfFreckles];
		int rank[] = new int[numberOfFreckles];
		for (int i = 0; i < numberOfFreckles; i++) {
			parent[i] = i;
		}
		
		// Calc distances
		for (int i = 0; i < numberOfFreckles; i++) {
			for (int j = 0; j < i; j++) {
				freckleDistances[i][j] = -1; // array is initialized to -1 in case of oopsies.
			}
		}
		for (int i = 0; i < numberOfFreckles; i++) {
			for (int j = 0; j < i; j++) {
				freckleDistances[i][j] = getDistance(freckleList[i][0],freckleList[i][1],freckleList[j][0],freckleList[j][1]);
			}
		}

		// Arrange edges into an array in order of shortest to longest
		// 3 columns: first for length of edge, second for origin, third for destination
		double listOfEdges[][] = new double[(numberOfFreckles*numberOfFreckles)/2][3];
		//loops through distances the number of times as there'll be edges
		for (int l = 0; l < (numberOfFreckles*numberOfFreckles)/2; l++) {
			//initializing the smallest edge this loop
			double smallest = Double.MAX_VALUE;
			int origin = -1;
			int destination = -1;
			//loops through columns
			for (int i = 0; i < numberOfFreckles; i++) {
				//loops through rows
				for (int j = 0; j < i; j++) {
					if (freckleDistances[i][j] < smallest && freckleDistances[i][j] > 0) {
						smallest = freckleDistances[i][j];
						origin = i;
						destination = j;
					}
				}
			}
			if (origin == -1) break; //breaks out of loop if we run into any unchanged initialized values (we're done)
			listOfEdges[l][0] = smallest;
			listOfEdges[l][1] = origin;
			listOfEdges[l][2] = destination;
			//System.out.println("This loop, smallist is edge " + smallest + " from " + origin + " to " + destination);
			if (freckleDistances[(int)listOfEdges[l][1]][(int)listOfEdges[l][2]] > 0) {
				freckleDistances[origin][destination] = Double.MAX_VALUE; //so that edge doesn't get picked back up
			}
		}
		
		// Loop through edges
		double distance = 0;
		for (int i = 0; i < listOfEdges.length; i++) {
			if (find((int)listOfEdges[i][1],parent) != find((int)listOfEdges[i][2], parent)) {
				union((int)listOfEdges[i][1], (int)listOfEdges[i][2], rank, parent);
				distance += listOfEdges[i][0];
				//trees are unioned if they can be
			}
		}		
		
		return distance;
	}
	
	public static void main(String[] args) {
		
		// Get First Input
		Scanner s = new Scanner(System.in);
		int numberOfCases = s.nextInt();
		
		// For each test case, this main loop occurs
		for (int i = 0; i < numberOfCases; i++) {
			
			// Get input of this test case
			int numberOfFreckles = s.nextInt();
			// Freckle locations are stored in a n x 2 array
			// They are numbered as they come off the input (first is 0, second is 1, etc)
			// Columns: one for each freckle. Row: one for x coord, one for y coord
			double[][] freckleList = new double[numberOfFreckles][2];
			
			for (int j = 0; j < numberOfFreckles; j++) {
				double x = s.nextDouble();
				double y = s.nextDouble();
				freckleList[j][0] = x;
				freckleList[j][1] = y;
			}
			// Output
			//System.out.printf("Prim:    %.2f\n", prim(freckleList));
			//System.out.printf("Kruskal: %.2f\n", kruskal(freckleList));
			System.out.printf("%.2f\n", kruskal(freckleList));
		}
		
		s.close();
	}

}