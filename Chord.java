/**
 * Class that contains the midi representation of notes in a chord and the name of the
 * chord itself. To be used in the ChordTraverser project.
 * By Micah Nacht
 * November 2015
 */

import java.util.*;

public class Chord{
	private List<Integer> notes;
	private String name;

	/**
	 * Constructor that takes in arguments for the note values and name of the chord.
	 */
	public Chord(String name, List<Integer> notes){
		this.notes = notes;
		this.name = name;
	}

	/**
	 * Returns the list of notes held in this chord.
	 */
	public List<Integer> getNotes(){
		return notes;
	}

	/**
	 * Returns a particular note in this chord.
	 */
	public int getNote(int index) throws IndexOutOfBoundsException{
		return notes.get(index);
	}

	/**
	 * Returs the name of this chord.
	 * TODO: combine with toString.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Also returns the name of this chord.
	 */
	public String toString(){
		return name;
	}
}