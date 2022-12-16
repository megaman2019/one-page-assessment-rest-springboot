package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    Optional<Note> findByApplication_IdAndNoteType(Integer appId, String noteType);
    Optional<Note> findById(Integer id);
    List<Note> findByApplication_Id(Integer appId);
}
