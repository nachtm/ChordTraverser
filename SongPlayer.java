/**
 * Takes a list of chords representing a song and plays it on the computer's 
 * default midi equipment. Uses a text file that defines chords as midi notes
 * to know what notes to play.
 * By Micah Nacht
 * November 2015
 */

import javax.sound.midi.*;
import java.util.*;

public class SongPlayer{
	Sequencer sequencer;
	Synthesizer synthesizer;
	Receiver receiver;

	/**
	 * Creates an instance of a SongPlayer by getting the default sequencer,
	 * synthesizer, and receiver from the computer. It's possible we don't need the 
	 * synthesizer.
	 * TODO: test without synthesizer reference.
	 */
	public SongPlayer() throws MidiUnavailableException{
		sequencer = MidiSystem.getSequencer();
		synthesizer = MidiSystem.getSynthesizer();
		receiver = MidiSystem.getReceiver(); //default speakers
		assert receiver != null;
	}	

	/**
	 * Plays a song represented by a list of Chord objects through the computer's
	 * default speakers. StartTick and the name of the method (playChord vs playSong)
	 * is a remnant from when I wanted to have this method play a single chord. Now
	 * it loops through the entire list of chords.
	 * TODO: remove startTick, rename to playSong
	 */
	public void playChord(List<Chord> c, int startTick){
		try{
			//Link sequencer and speakers
			sequencer.open();
			sequencer.getTransmitter().setReceiver(receiver);

			//Create a track to add to the sequence, which is then added to the
			//sequencer.
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track song = seq.createTrack();

			//Add chords note by note to the track
			for(int i = 0; i < c.size(); i++){
				List<Integer> notes = c.get(i).getNotes();
				for(int note : notes){
					song.add(new MidiEvent(new ShortMessage(
						ShortMessage.NOTE_ON, 0, note, 93), startTick + i * 10));
					song.add(new MidiEvent(new ShortMessage(
						ShortMessage.NOTE_OFF, 0, note, 93), startTick + i * 10 + 10));	
				}
			}
			
			//Add track to the sequence and begin playback.
			sequencer.setSequence(seq);
			sequencer.start();

			//Close the sequencer on the end of the song.
			sequencer.addMetaEventListener(new MetaEventListener() {
				public void meta(MetaMessage msg){
					if(msg.getType() == 0x2F){
						sequencer.stop();
						sequencer.close();
					}
				}
			});
			
		} catch(Exception e){
			System.err.println(e);
		}


	}

	/**
	 * Use the main method to test functionality of SongPlayer. I still need to write
	 * tests. 
	 */
	public static void main(String[] args){
	}
}