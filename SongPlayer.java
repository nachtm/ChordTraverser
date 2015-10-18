import javax.sound.midi.*;
import java.util.*;

public class SongPlayer{
	Sequencer sequencer;
	Synthesizer synthesizer;
	Receiver receiver;

	public SongPlayer() throws MidiUnavailableException{
		sequencer = MidiSystem.getSequencer();
		synthesizer = MidiSystem.getSynthesizer();
		receiver = MidiSystem.getReceiver(); //default speakers
		assert receiver != null;
		// assert synthesizer != null;
		// assert sequencer != null; 

		//if I want to use any of those:
	// 	if(!sequencer.isOpen()){
	// 		try{
	// 			sequencer.open();
	// 		} catch(MidiUnavailableException e){
	// 			System.err.println(e);
	// 			System.exit(0); //:(
	// 		}
	// 	}

	// 	//close receiver when done
	// 	sequencer.close();
		
	}	

	public void playChord(List<Chord> c, int startTick){
		//System.out.println("adding " + c + " at " + startTick);
		try{
			sequencer.open();
			sequencer.getTransmitter().setReceiver(receiver);	
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track song = seq.createTrack();

			for(int i = 0; i < c.size(); i++){
				List<Integer> notes = c.get(i).getNotes();
				for(int note : notes){
					song.add(new MidiEvent(new ShortMessage(
						ShortMessage.NOTE_ON, 0, note, 93), startTick + i * 10));
					song.add(new MidiEvent(new ShortMessage(
						ShortMessage.NOTE_OFF, 0, note, 93), startTick + i * 10 + 10));	
				}
			}
			
			sequencer.setSequence(seq);
			// System.out.println("numTracks of sequencer: " + sequencer.getSequence().getTracks().length);
			// System.out.println("sequencer contains song: " + (song == sequencer.getSequence().getTracks()[0]));
			// System.out.println("size of track: " + song.size());
			//System.out.println(song.get(2));
			sequencer.start();

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

	public static void main(String[] args){
		// List<Integer> cMajNotes = new ArrayList<Integer>();
		// cMajNotes.addAll(Arrays.asList(60,64,67));
		// Chord cMaj = new Chord("I", cMajNotes);
		// try{
		// 	SongPlayer s = new SongPlayer();
		// 	s.playChord(cMaj, 0);

		// } catch(MidiUnavailableException e){
		// 	System.err.println(e);
		// 	System.exit(0);
		// }
	}
}