package org.homework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.homework.model.MusicPiece;
import org.homework.model.Note;
import org.homework.service.TransposerService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void testTransposeNote() {
        Note originalNote = new Note(2, 1);
        Note transposedNote = TransposerService.transposeNote(originalNote, 3);
        assertEquals(new Note(2, 4), transposedNote);
    }

    @Test
    public void testTransposeMusicPiece() {
        List<Note> notes = Arrays.asList(new Note(2, 1), new Note(1, 10),
                new Note(1, 5));
        MusicPiece originalMusicPiece = new MusicPiece(notes);
        MusicPiece transposedMusicPiece = TransposerService.transposeMusicPiece(originalMusicPiece, -2);

        List<Integer> expectedNoteNumbers = Arrays.asList(11, 8, 3);

        List<Integer> actualNoteNumbers = transposedMusicPiece.getNotes().stream()
                .map(Note::getNoteNumber)
                .collect(Collectors.toList());

        assertEquals(expectedNoteNumbers, actualNoteNumbers);
    }

    @Test
    public void testTransposeWithSampleInput() throws Exception {
        MusicPiece originalMusicPiece = new MusicPiece(Arrays.asList(
                new Note(2, 1), new Note(2, 6), new Note(2, 1), new Note(2, 8), new Note(2, 1),
                new Note(2, 9), new Note(2, 1), new Note(2, 6), new Note(2, 1), new Note(2, 8),
                new Note(2, 1), new Note(2, 9), new Note(2, 1), new Note(2, 11), new Note(2, 1),
                new Note(2, 8), new Note(2, 1), new Note(2, 9), new Note(2, 1), new Note(2, 11),
                new Note(2, 1), new Note(3, 1), new Note(2, 1), new Note(2, 9), new Note(2, 1),
                new Note(2, 11), new Note(2, 1), new Note(3, 1), new Note(2, 1), new Note(3, 2),
                new Note(2, 1), new Note(2, 11), new Note(2, 1), new Note(3, 1), new Note(2, 1),
                new Note(2, 9), new Note(2, 1), new Note(2, 11), new Note(2, 1), new Note(2, 8),
                new Note(2, 1), new Note(2, 9), new Note(2, 1), new Note(2, 6), new Note(2, 1),
                new Note(2, 8), new Note(2, 1), new Note(2, 5), new Note(2, 1), new Note(2, 6),
                new Note(2, 1), new Note(2, 1), new Note(2, 1), new Note(2, 2), new Note(2, 1),
                new Note(1, 11), new Note(2, 1), new Note(2, 1), new Note(2, 1), new Note(1, 9),
                new Note(2, 1), new Note(1, 11), new Note(2, 1), new Note(1, 8), new Note(2, 1),
                new Note(1, 9), new Note(2, 1), new Note(1, 6), new Note(2, 1), new Note(1, 11),
                new Note(2, 1), new Note(1, 8), new Note(2, 1), new Note(1, 9), new Note(2, 1),
                new Note(1, 6), new Note(2, 1), new Note(1, 8), new Note(2, 1), new Note(1, 5),
                new Note(2, 1), new Note(1, 6)
        ));

        // When transposing by -3 semitones
        MusicPiece transposedMusicPiece = TransposerService.transposeMusicPiece(originalMusicPiece, -3);

        // Then the expected transposed music piece
        String jsonArrayString = "[[1,10],[2,3],[1,10],[2,5],[1,10],[2,6],[1,10],[2,3],[1,10],[2,5],[1,10],[2,6],[1,10]," +
                "[2,8],[1,10],[2,5],[1,10],[2,6],[1,10],[2,8],[1,10],[2,10],[1,10],[2,6],[1,10],[2,8],[1,10],[2,10],[1,10]," +
                "[2,11],[1,10],[2,8],[1,10],[2,10],[1,10],[2,6],[1,10],[2,8],[1,10],[2,5],[1,10],[2,6],[1,10],[2,3],[1,10]," +
                "[2,5],[1,10],[2,2],[1,10],[2,3],[1,10],[1,10],[1,10],[1,11],[1,10],[1,8],[1,10],[1,10],[1,10],[1,6],[1,10]," +
                "[1,8],[1,10],[1,5],[1,10],[1,6],[1,10],[1,3],[1,10],[1,8],[1,10],[1,5],[1,10],[1,6],[1,10],[1,3],[1,10]," +
                "[1,5],[1,10],[1,2],[1,10],[1,3]]";

        List<List<Integer>> noteJsonList = new ObjectMapper().readValue(jsonArrayString, new TypeReference<>() {});

        // Convert the List<List<Integer>> to List<Note>
        List<Note> expectedNotes = noteJsonList.stream()
                .map(noteJson -> new Note(noteJson.get(0), noteJson.get(1)))
                .collect(Collectors.toList());


        MusicPiece expectedMusicPiece = new MusicPiece(expectedNotes);
        assertEquals(originalMusicPiece.getNotes().size(), expectedNotes.size());
        assertEquals(expectedMusicPiece, transposedMusicPiece);
    }


}