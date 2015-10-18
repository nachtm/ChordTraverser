import java.util.*;

public class Chord{
	private List<Integer> notes;
	private String name;

	public Chord(String name, List<Integer> notes){
		this.notes = notes;
		this.name = name;
	}

	public List<Integer> getNotes(){
		return notes;
	}

	public int getNote(int index) throws IndexOutOfBoundsException{
		return notes.get(index);
	}

	public String getName(){
		return name;
	}

	public String toString(){
		return name;
	}
}