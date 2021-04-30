import java.util.ArrayList;
public class Graph {
	// The adjacency list of each vertex, stored into one array
	ArrayList<Integer>[] adjacencyL;
	
	// Same idea with the distances
	ArrayList<Double>[] distances;
	
	@SuppressWarnings("unchecked")
	Graph(int numOfVertices){
		
		// create the arrays
		adjacencyL = (ArrayList<Integer>[]) new ArrayList[numOfVertices];
		distances =(ArrayList<Double>[]) new ArrayList[numOfVertices];
		
		// create the array lists in the arrays
		for(int i = 0; i<numOfVertices;i++) {
			adjacencyL[i] = new ArrayList<Integer>(0);
			distances[i] = new ArrayList<Double>(0);
		}
	}
	/**
	 * 
	 * @param vA - vertex A
	 * @param vB - vertex B
	 * @param distance - length of edge AB (so distance between them)
	 */
	public void addEdge(int vA, int vB, double distance) {
		
		// update adjacency list
		adjacencyL[vA].add(vB);
		
		// record distance
		distances[vA].add(distance);
	}
	 
	/** This implentation will only work if there are no negative edges
	 * 
	 * @param vA - vertex A
	 * @param vB - vertex B
	 * @return double - distance between them:
	 * 						- 0 if A==B
	 * 						- infinity, if B is not adjacent to A (so distance is unknown) 
	 * 						- the distance if B is adjacent to A  (so distance is known)
	 */
	public double getDistance(int vA, int vB) {
		if(vA == vB) return 0;
		int vIndex = -1;
		for(int i = 0;i<adjacencyL[vA].size();i++) {
			if(adjacencyL[vA].get(i)==vB) vIndex =  i;
		}
		 if(vIndex>=0) return distances[vA].get(vIndex);
		 return Double.POSITIVE_INFINITY;
	}
}
