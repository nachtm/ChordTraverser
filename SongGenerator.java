import java.io.*;
import java.util.*;

public class SongGenerator{
	private Graph chordGraph;
	private List<String> chords; //eventually chords will probably be their own objects
	private Random rand = new Random();

	public SongGenerator(Graph g, List<String> names){
		chordGraph = g;
		chords = names;
	}

	public String generateSong(){
		String song = chords.get(0);
		int currChord = rand.nextInt(chords.size());
		while(currChord != 0){
			song += " " + chords.get(currChord);
			List<Integer> neighbors = chordGraph.getNeighbors(currChord);
			int nextIndex = rand.nextInt(neighbors.size());
			currChord = neighbors.get(nextIndex);
		}
		assert currChord == 0;
		song += " " + chords.get(0);
		return song;
	}
	//generate graph from a text file
	public static void main(String[] args){
		if(args.length != 1){
			System.exit(0);
		}
		File f = new File(args[0]);

		
		Scanner s = null; //will never be null past the try/catch block below
		try{
			s = new Scanner(f);
		} catch(FileNotFoundException e){
			System.err.println(e);
			System.exit(0);
		}
		assert s != null; 
		
		int numChords = s.nextInt();
		s.nextLine(); //have to eat the newline char

		Graph g = new Graph(numChords);
		List<String> chordNames = new ArrayList<String>();
		for(int i = 0; i < numChords; i++){
			String line = s.nextLine();
			Scanner lineReader = new Scanner(line);
			chordNames.add(lineReader.next());
			while(lineReader.hasNextInt()){
				g.addEdge(i, lineReader.nextInt());
			}
		}

		SongGenerator sg = new SongGenerator(g, chordNames);
		System.out.println(sg.generateSong());

		// for(String chord : chordNames){
		// 	System.out.print(chord + " ");
		// }
		// System.out.println();
		// System.out.println(g);


	}
}