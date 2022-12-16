package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Note;
import com.readysetsoftware.creditassessmentapi.data.payload.request.NoteRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NoteService {
    Note updateNote(Integer appId, NoteRequest noteRequest);
    List<Note> findAllNotes(Integer appId);
    Optional<Note> findNoteById(Integer id);
    Optional<Note> findByApplication_IdAndNoteType(Integer appId, String noteType);
}
