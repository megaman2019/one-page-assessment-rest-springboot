package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.AssessmentResult;
import com.readysetsoftware.creditassessmentapi.data.model.Note;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssessmentResultRequest;
import com.readysetsoftware.creditassessmentapi.data.payload.request.NoteRequest;
import com.readysetsoftware.creditassessmentapi.data.payload.response.NoteResponse;
import com.readysetsoftware.creditassessmentapi.service.AssessmentResultService;
import com.readysetsoftware.creditassessmentapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PutMapping("/update/{appId}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable("appId") Integer appId, @Valid @RequestBody NoteRequest noteRequest){
        Note updatedNote = noteService.updateNote(appId, noteRequest);
        NoteResponse noteResponse = new NoteResponse(
            updatedNote.getId(),
            updatedNote.getAppId(),
            updatedNote.getNoteType(),
            updatedNote.getNoteDesc(),
            updatedNote.getUserId()
        );
        return new ResponseEntity<NoteResponse>(noteResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/find/all/{appId}", produces = "application/json")
    public ResponseEntity<List<Note>> getAllNotes(@PathVariable("appId") Integer appId) {
        List<Note> notes = noteService.findAllNotes(appId);

        return new ResponseEntity<> (notes, HttpStatus.OK);
    }

    @GetMapping(value = "/find/{id}", produces = "application/json")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable("id") Integer id) {
        Optional<Note> note = noteService.findNoteById(id);

        NoteResponse noteResponse = new NoteResponse(
            note.get().getId(),
            note.get().getAppId(),
            note.get().getNoteType(),
            note.get().getNoteDesc(),
            note.get().getUserId()
        );
        return new ResponseEntity<NoteResponse> (noteResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/find/{appId}/{noteType}", produces = "application/json")
    public ResponseEntity<NoteResponse> getNoteByAppIdAndNoteType(@PathVariable("appId") Integer appId, @PathVariable("noteType") String noteType) {
        Optional<Note> note = noteService.findByApplication_IdAndNoteType(appId, noteType);
        NoteResponse noteResponse = new NoteResponse();
        if (note.isPresent()) {
            noteResponse = new NoteResponse(
                note.get().getId(),
                note.get().getAppId(),
                note.get().getNoteType(),
                note.get().getNoteDesc(),
                note.get().getUserId()
            );
        }

        return new ResponseEntity<NoteResponse> (noteResponse, HttpStatus.OK);
    }
}
