// Rian Blahut CSCI3101 Extra Credit Problem

package orderingtasks;

import java.util.*;

class Graph {
	private int n; // number of tasks
	private int m; // number of relations
	private LinkedList<Integer> adj[]; // list of tasks
	
	// Constructor
	Graph(int n, int m) {
		this.n = n;
		this.m = m;
		adj = new LinkedList[n+1]; // n things in the list
		//System.out.println("New LL with " + n + " items created");
		for(int i = 1; i <= n; ++i) {
			adj[i] = new LinkedList(); // new linked list at each item, showing adjacent items
			//System.out.println("Adj LL created at position " + i);
		}
	}
	void addEdge(int v, int w) {
		adj[v].add(w);
		//System.out.println("Item " + w + " added adjacent to " + v);
	}
	void dfsvisit(int v, boolean visited[], Stack stack) {
		visited[v] = true;
		//System.out.println("visiting! item " + v + " is now true");
		Integer i;
		Iterator<Integer> it = adj[v].iterator();
		while (it.hasNext()) {
			i = it.next();
			//System.out.println("At adj[" + v + "], i = " + i);
			if (!visited[i]) {
				//System.out.println(i + " hasn't been visited?");
				dfsvisit(i, visited, stack);
			}
		}
		stack.push(new Integer(v));
	}
	void topoSort() {
		Stack stack = new Stack();
		
		boolean visited[] = new boolean[n+1];
		for (int i = 1; i <= n; i++) visited[i] = false;
		for (int i = 1; i <= n; i++) {
			if (visited[i] == false) {
				//System.out.println((i) + " hasn't been visited yet");
				dfsvisit(i, visited, stack);
			}
		}
		while (stack.empty() == false) System.out.print(stack.pop() + " ");
	}
}
public class OrderingTasks {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		while (true) {
			int n = s.nextInt(); // number of tasks (1-n)
			int m = s.nextInt(); // number of direct precedence relations
			//System.out.println("n = " + n + ", m = " + m);
			
			if (n == 0 && m == 0) break;
			
			Graph taskList = new Graph(n, m);

			for (int x = 0; x < m; x++) {
				int i = s.nextInt(); //this task must be completed prior to next
				int j = s.nextInt();
				//System.out.print(i + " ");
				//System.out.println(j);
				taskList.addEdge(i, j);
			}
			taskList.topoSort();
			System.out.println();
		}

		
		s.close();
	}

}
