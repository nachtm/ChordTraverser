import java.io.*;
import java.util.*;

public class SongGenerator{
	private Graph chordGraph;
	private List<Chord> chords; //eventually chords will probably be their own objects
	private Random rand = new Random();
	private Map<String,List<Integer>> chordDictionary;

	public SongGenerator(Graph g, List<String> names, Map<String, List<Integer>> dict){
		chordGraph = g;
		this.chordDictionary = dict;
		chords = new ArrayList<Chord>();
		for(String name : names){
			Chord c = new Chord(name, dict.get(name));
			chords.add(c);
		}
	}

	// public String generateSong(){
	// 	String song = chords.get(0);
	// 	int currChord = rand.nextInt(chords.size());
	// 	while(currChord != 0){
	// 		song += " " + chords.get(currChord);
	// 		List<Integer> neighbors = chordGraph.getNeighbors(currChord);
	// 		int nextIndex = rand.nextInt(neighbors.size());
	// 		currChord = neighbors.get(nextIndex);
	// 	}
	// 	assert currChord == 0;
	// 	song += " " + chords.get(0);
	// 	return song;
	// }

	public List<Chord> generateSong(){
		List<Chord> song = new ArrayList<Chord>();
		song.add(chords.get(0));
		int currChord = rand.nextInt(chords.size());
		while(currChord != 0){
			song.add(chords.get(currChord));
			List<Integer> neighbors = chordGraph.getNeighbors(currChord);
			int nextIndex = rand.nextInt(neighbors.size());
			currChord = neighbors.get(nextIndex);
		}
		assert currChord == 0;
		song.add(chords.get(0));
		return song;
	}

	//generate graph from a text file
	public static void main(String[] args){
		if(args.length != 2){
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

		//generate chord dictionary from a text file
		Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();

		f = new File(args[1]);
		s = null; //will never be null past the try/catch block below
		try{
			s = new Scanner(f);
		} catch(FileNotFoundException e){
			System.err.println(e);
			System.exit(0);
		}
		assert s != null; 

		while(s.hasNextLine()){
			String line = s.nextLine();
			Scanner lr = new Scanner(line);
			String name = lr.next();
			List<Integer> notes = new ArrayList<Integer>();
			while(lr.hasNextInt()){
				notes.add(lr.nextInt());
			}
			dict.put(name, notes);
		}
		System.out.println(dict.size());

		SongGenerator sg = new SongGenerator(g, chordNames, dict);
		List<Chord> song = sg.generateSong();
		System.out.println(song);
		try{
			SongPlayer output = new SongPlayer();
			output.playChord(song, 0);
		} catch(Exception e){
			System.err.println(e);
		}
		// for(String chord : chordNames){
		// 	System.out.print(chord + " ");
		// }
		// System.out.println();
		// System.out.println(g);


	}
}