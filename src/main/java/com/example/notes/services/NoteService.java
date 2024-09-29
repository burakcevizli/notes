package com.example.notes.services;

import com.example.notes.models.Note;

import java.util.List;

public interface NoteService {
    Note createNoteForUser(String userName, String content);

    Note updateNoteForUser(Long noteId, String content, String ownerUsername);

    void deleteNoteForUser(Long noteId, String userName);

    List<Note> getNotesForUser(String userName);
}
