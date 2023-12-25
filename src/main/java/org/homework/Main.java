package org.homework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.homework.exception.NonValidDataException;
import org.homework.model.MusicPiece;
import org.homework.model.Note;
import org.homework.service.TransposerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.homework.service.TransposerService.transposeMusicPiece;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (args.length != 2) {
            System.out.println("Usage: java Transposer <input_file> <semitones_to_transpose>");
            return;
        }

        String inputFile = args[0];
        int semitonesToTranspose = Integer.parseInt(args[1]);

        try {
            String json = readFileAsString(inputFile);
            List<List<Integer>> noteJsonList = objectMapper.readValue(json, new TypeReference<>() {});

            // Convert the List<List<Integer>> to List<Note>
            List<Note> notes = noteJsonList.stream()
                    .map(noteJson -> new Note(noteJson.get(0), noteJson.get(1)))
                    .collect(Collectors.toList());

            MusicPiece originalMusicPiece = new MusicPiece(notes);

            MusicPiece transposedMusicPiece = transposeMusicPiece(originalMusicPiece, semitonesToTranspose);

            boolean isValid = transposedMusicPiece.getNotes().stream()
                    .allMatch(TransposerService::isNoteWithinRange);

            if (isValid) {
                // Convert the List<Note> back to List<NoteJson>
                List<List<Integer>> transposedNotes = transposedMusicPiece.getNotes().stream()
                        .map(note -> Arrays.asList(note.getOctave(), note.getNoteNumber()))
                        .collect(Collectors.toList());

                // Serialize List<NoteJson> to JSON
                String resultJson = objectMapper.writeValueAsString(transposedNotes);
                Files.write(Paths.get("result_transposed.json"), resultJson.getBytes());
                System.out.println("Transposition successful. Output written to output_transposed.json");
            } else {
                throw new NonValidDataException("Error: Transposed notes fall out of the keyboard range.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static String readFileAsString(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }
}