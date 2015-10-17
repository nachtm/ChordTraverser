import java.util.*;

public class Graph{
	private List<List<Boolean>> adjMatrix;
	private int size;

	public Graph(int size){
		adjMatrix = new ArrayList<List<Boolean>>(); 
		for(int i = 0; i < size; i++){
			addVertex();
		}
	}

	public boolean isAdjacent(int x, int y){
		return adjMatrix.get(x).get(y);
	}

	public List<Integer> getNeighbors(int x){
		List<Integer> toReturn = new ArrayList<Integer>();

		for(int i = 0; i < adjMatrix.size(); i++){
			if(adjMatrix.get(x).get(i)){
				toReturn.add(i);
			}
		}
		return toReturn;
	}

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

		//System.out.println(this);
	}

	//I don't think we'll need a removeVertex function but if we do it will go here

	public void addEdge(int a, int b){
		adjMatrix.get(a).set(b, true);
	}

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

	public static void main(String[] args){
		Graph g = new Graph(5);
		g.addEdge(0,1);
		System.out.println(g);
		System.out.println("Adjacency test, should be true: " + g.isAdjacent(0,1));
	}
}