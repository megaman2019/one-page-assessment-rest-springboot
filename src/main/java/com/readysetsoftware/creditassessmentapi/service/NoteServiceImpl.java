package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.Note;
import com.readysetsoftware.creditassessmentapi.data.payload.request.NoteRequest;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.NoteRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Note updateNote(Integer appId, NoteRequest noteRequest){

        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new ApiRequestException("No Application found for id : " + appId));
        try {

            int noteId = noteRequest.getId() == null ? 0 : noteRequest.getId();

            return noteRepository.findById(noteId)
                // if exist then update
                .map(updatedNote -> {
                    updatedNote.setNoteType(noteRequest.getNoteType());
                    updatedNote.setNoteDesc(noteRequest.getNoteDesc());
                    updatedNote.setUserId(noteRequest.getUserId());
                    return noteRepository.save(updatedNote);
                })
                // if note does not exist then create
                .orElseGet(() -> {
                    Note newNote = new Note();
                    newNote.setAppId(noteRequest.getAppId());
                    newNote.setNoteType(noteRequest.getNoteType());
                    newNote.setNoteDesc(noteRequest.getNoteDesc());
                    newNote.setUserId(noteRequest.getUserId());
//                        newNote.setApplication(application);

                    return noteRepository.save(newNote);
                });

        } catch (Exception ex) {
            throw new ApiRequestException("Update failed: " + ex.getMessage());
        }

    }

    @Override
    public List<Note> findAllNotes(Integer appId) {
        return noteRepository.findByApplication_Id(appId);
    }

    @Override
    public Optional<Note> findNoteById(Integer id) {
        return noteRepository.findById(id);
    }

    @Override
    public Optional<Note> findByApplication_IdAndNoteType(Integer appId, String noteType){
        return noteRepository.findByApplication_IdAndNoteType(appId, noteType);
    }





}
