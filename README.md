# ChordTraverser
Builds chord progressions by walking through a chord graph with some randomness.

To run, run the following command from terminal or command prompt:

java SongGenerator chordgraph.txt chordDef.txt

SongGenerator takes two commandline arguments, a map of the connections between chords and a chord definition file that tells the program what chord corresponds to what note. The names given to the chords don't matter when connecting a chord to its definition; instead, ensure that the order of chords is the same in both files.
