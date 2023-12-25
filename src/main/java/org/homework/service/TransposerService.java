package org.homework.service;

import org.homework.model.MusicPiece;
import org.homework.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public class TransposerService {

    private static final int NOTES_IN_OCTAVE = 12;
    private static final int MIN_OCTAVE = -3;
    private static final int MAX_OCTAVE = 5;
    private static final int MIN_NOTE_NUMBER = 1;
    private static final int MAX_NOTE_NUMBER = 12;


    public static Note transposeNote(Note note, int semitones) {

        int transposedNoteNumber = (note.getNoteNumber() + semitones) % NOTES_IN_OCTAVE;
        int transposedOctave = note.getOctave() + (note.getNoteNumber() + semitones) / NOTES_IN_OCTAVE;

        // Adjust the octave if the resulting note is out of valid range
        if (transposedNoteNumber < MIN_NOTE_NUMBER) {
            transposedNoteNumber += NOTES_IN_OCTAVE;
            transposedOctave--;
        } else if (transposedNoteNumber > MAX_NOTE_NUMBER) {
            transposedNoteNumber -= NOTES_IN_OCTAVE;
            transposedOctave++;
        }

        return new Note(transposedOctave, transposedNoteNumber);
    }

    public static MusicPiece transposeMusicPiece(MusicPiece musicPiece, int semitones) {
        List<Note> transposedNotes = musicPiece.getNotes()
                .stream()
                .map(note -> transposeNote(note, semitones))
                .collect(Collectors.toList());
        return new MusicPiece(transposedNotes);
    }

    public static boolean isNoteWithinRange(Note note) {
        return note.getOctave() >= MIN_OCTAVE && note.getOctave() <= MAX_OCTAVE &&
                note.getNoteNumber() >= MIN_NOTE_NUMBER && note.getNoteNumber() <= MAX_NOTE_NUMBER;
    }
}
