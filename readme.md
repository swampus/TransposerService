**Usage:** java Transposer <input_file> <semitones_to_transpose>
will generate **result_transposed.json** in same root as executable jar.

Semitone transposition algorithm.
The algorithm itself is a practical application of musical theory principles.

**Transpose Note Function:**
Take a single note [octave, noteNumber] and a number of semitones to transpose by.
Calculate the new note number and octave after transposition.
Return the transposed note [transposedOctave, transposedNoteNumber].
_The time complexity is constant, O(1)._

**Transpose Music Piece Function:**
Iterate through each note in the original music piece.
Use the Transpose Note function to get the transposed note for each original note.
Return the transposed music piece.
_time complexity is O(n)_

**Check Note Range Function**
Take a note [octave, noteNumber] and check if it falls within the valid keyboard range.
Return true if the note is within the valid range, false otherwise.
_The time complexity is constant, O(1)._

