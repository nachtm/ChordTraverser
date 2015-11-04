/**
 * Main class in the ChordTraverser program. This reads in two text files to 
 * generate a graph of chords that sound good in sequence. From there it plays the
 * root chord then jumps to a random neighbor, continuing to traverse the graph 
 * until the minimum length is reached AND it arrives at the root again. Once it's 
 * generated the song, it plays it using MIDI and the computer's default speakers.
 * By Micah Nacht 
 * November 2015 
 */
import java.io.*;
import java.util.*;

public class SongGenerator{
	private Graph chordGraph;
	private List<Chord> chords; 
	private Random rand = new Random();
	private Map<String,List<Integer>> chordDictionary;
	private static final int MINIMUM_LENGTH = 15; //Minimum number of chords in a song

	/**
	 * Constructor for a SongGenerator. Takes in a graph which represents connections 
	 * between chords "g", a list of chord names "names" (each node in g is an 
	 * integer, and the name at the corresponding index of "names" is the name of 
	 * the chord), and a Map that maps chord names to the corresponding MIDI notes.
	 */
	public SongGenerator(Graph g, List<String> names, Map<String, List<Integer>> dict){
		chordGraph = g;
		this.chordDictionary = dict;
		chords = new ArrayList<Chord>();
		for(String name : names){
			Chord c = new Chord(name, dict.get(name));
			chords.add(c);
		}
	}

	/**
	 * Generates a song by randomly walking through the chordGraph passed to this
	 * SongGenerator by the constructor. 
	 */
	public List<Chord> generateSong(){
		List<Chord> song = new ArrayList<Chord>();
		song.add(chords.get(0));
		int currChord = rand.nextInt(chords.size());
		while(currChord != 0 || song.size() < MINIMUM_LENGTH){
			song.add(chords.get(currChord));
			List<Integer> neighbors = chordGraph.getNeighbors(currChord);
			int nextIndex = rand.nextInt(neighbors.size());
			currChord = neighbors.get(nextIndex);
		}
		assert currChord == 0;
		song.add(chords.get(0));
		return song;
	}

	/**
	 * Reads the two text files and turns them into a graph, a list of chord names, 
	 * and a map between chord names and notes to be used by the SongGenerator. 
	 * Then, tries to play the song through the computer's speakers by creating a 
	 * SongPlayer object and 
	 */
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
			lineReader.next(); //skip the number
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
	}
}