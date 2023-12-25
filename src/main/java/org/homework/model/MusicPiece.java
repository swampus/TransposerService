package org.homework.model;

import java.util.List;
import java.util.Objects;

public class MusicPiece {
    private List<Note> notes;

    public MusicPiece(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return notes.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicPiece that = (MusicPiece) o;
        return Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes);
    }
}
