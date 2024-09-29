package com.example.notes.servicesImpl;

import com.example.notes.models.Note;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNoteForUser(String userName, String content){
        Note note = new Note();
        note.setNote(content);
        note.setOwnerUsername(userName);
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String ownerUsername){
        Note note = noteRepository.findById(noteId).orElseThrow(()->new RuntimeException("Note not found"));
        note.setNote(content);
        Note updatedNote = noteRepository.save(note);
        return note;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String userName){
        noteRepository.deleteById(noteId);
    }

    @Override
    public List<Note> getNotesForUser(String userName) {
        List<Note> notes = noteRepository.findByOwnerUsername(userName);
        return notes;
    }
}
