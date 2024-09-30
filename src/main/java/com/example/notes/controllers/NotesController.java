package com.example.notes.controllers;

import com.example.notes.models.Note;
import com.example.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) { // Burdaki authentication principal annatasyonu kullanıcının
        String userName = userDetails.getUsername();                                                        //oturuma giriş yopatıktan sonra otomatik olarak user bilgilerini almasını sağlar.
        System.out.println("USER DETAILS : " + userName);
        return noteService.createNoteForUser(userName, content);
    }

    @GetMapping
    public List<Note> getAllNotes(@AuthenticationPrincipal UserDetails userDetails) { // Ayrıca bu annatasyon giriş ypauılan kullanıcının verilerini otomatik olarak ekliyor.
        String userName = userDetails.getUsername();
        System.out.println("USER DETAILS : " + userName);
        return noteService.getNotesForUser(userName);
    }

    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable long noteId, @RequestBody Map<String, String> requestBody, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        String content = requestBody.get("content"); // "content" anahtarını kullanarak içeriği al

        System.out.println("USER DETAILS : " + userName);
        return noteService.updateNoteForUser(noteId, content, userName); // Kullanıcı adını geç
    }


    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        noteService.deleteNoteForUser(noteId, userName);
    }
}
