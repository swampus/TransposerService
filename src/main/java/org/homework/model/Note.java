package org.homework.model;

import java.util.Objects;

public class Note {
    private int octave;
    private int noteNumber;

    public Note(int octave, int noteNumber) {
        this.octave = octave;
        this.noteNumber = noteNumber;
    }

    public int getOctave() {
        return octave;
    }

    public int getNoteNumber() {
        return noteNumber;
    }

    @Override
    public String toString() {
        return "[" + octave + ", " + noteNumber + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return octave == note.octave && noteNumber == note.noteNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(octave, noteNumber);
    }
}
