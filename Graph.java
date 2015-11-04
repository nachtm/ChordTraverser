/**
 * Basic graph implementation using an adjacency matrix. To be used in 
 * a project to randomly generate songs.
 * By Micah Nacht 
 * November 2015
 */
import java.util.List;
import java.util.ArrayList;

public class Graph{
	private List<List<Boolean>> adjMatrix;
	private int size;

	/**
	 * Constructs the graph, taking an initial size as input. The initial size 
	 * should be equal to the number of intended nodes in the graph, though the 
 	 * graph can add both vertices and edges so it's better to err on the side 
 	 * of too small. 
	 */
	public Graph(int size){
		adjMatrix = new ArrayList<List<Boolean>>(); 
		for(int i = 0; i < size; i++){
			addVertex();
		}
	}
	/**
	 * Returns true if two nodes are adjacent, and false otherwise. 
	 */
	public boolean isAdjacent(int x, int y) throws IndexOutOfBoundsException{
		return adjMatrix.get(x).get(y);
	}

	/**
	 * Returns a list of integers representing the neighbors of the node given as
	 * an argument.  
	 */
	public List<Integer> getNeighbors(int x) throws IndexOutOfBoundsException{
		//Initialize the list
		List<Integer> toReturn = new ArrayList<Integer>();

		//Add elements to list if they're equal to true in the matrix
		for(int i = 0; i < adjMatrix.size(); i++){
			if(adjMatrix.get(x).get(i)){
				toReturn.add(i);
			}
		}
		return toReturn;
	}

	/**
	 * Increase the size of the graph by 1. 
	 */
	public void addVertex(){
		//add a new column to the side
		for(int i = 0; i < size; i++){
			adjMatrix.get(i).add(false);
		}
		size++;
		List<Boolean> newList = new ArrayList<Boolean>();
		
		//add a new row to the bottom
		for(int i = 0; i < size; i++){
			newList.add(false);
		}
		adjMatrix.add(newList);
	}

	/**A removeVertex function is never necessary (we only want to add chords 
	 * to the graph, never delete them) so I omitted it from this implementation.
	 */

	/**
	 * Sets the connection between two nodes to true. 
	 */	
	public void addEdge(int a, int b) throws IndexOutOfBoundsException{
		adjMatrix.get(a).set(b, true);
	}

	/**
	 * Sets the connection between two nodes to false.
	 */
	public void removeEdge(int a, int b) throws IndexOutOfBoundsException{
		adjMatrix.get(a).set(b, false);
	}

	/**
	 * Returns the number of nodes in the graph.
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Returns a string representation of this graph useful for debugging.
	 */
	public String toString(){
		String s = "";
		s += "Graph size: " + size + "\n";
		for(List<Boolean> row : adjMatrix){
			//System.out.print("R\t");
			for(Boolean b : row){
				s += b + "\t";
			}
			s += "\n";
		}	
		return s;
	}

	/**
	 * VERY rudimentary testing to ensure everything works (mostly) properly. 
	 * I need to create a more robust set of tests still. 
	 */
	public static void main(String[] args){
		Graph g = new Graph(5);
		g.addEdge(0,1);
		System.out.println(g);
		System.out.println("Adjacency test, should be true: " + g.isAdjacent(0,1));
	}
}