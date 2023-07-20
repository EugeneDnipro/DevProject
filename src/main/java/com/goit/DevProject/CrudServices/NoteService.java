package com.goit.DevProject.CrudServices;

import com.goit.DevProject.Entities.AccessType;
import com.goit.DevProject.Entities.Note;
import com.goit.DevProject.Repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    public final NoteRepository repository;

    public Map<Long, Note> listAll() {
        return repository.findAll().stream().collect(Collectors.toMap(Note::getId, note -> note));
    }

    public Note add(Note note) {
        return repository.saveAndFlush(note);
    }

    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("There is not any note to DELETE with such ID");
        }
        repository.deleteById(id);
    }

    public void update(Note note) {
        if (!repository.existsById(note.getId())) {
            throw new IllegalArgumentException("There is not any note to UPDATE with such ID");
        }
        Long id = note.getId();
        repository.getReferenceById(id).setTitle(note.getTitle());
        repository.getReferenceById(id).setContent(note.getContent());
        repository.getReferenceById(id).setAccess(note.getAccess());
        repository.saveAndFlush(note);
    }

    public Note getById(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("There is not any note to GET with such ID");
        }
        return repository.getReferenceById(id);
    }

    public boolean isAccessFlag(Note note) {
        return note.getAccess() == AccessType.PRIVATE ? true : false;
    }

    public String validateNote(Note note) {
        String response = "OK";
        if ((note.getTitle().length() < 5 || note.getTitle().length() > 100) && (note.getContent().length() < 5 || note.getContent().length() > 10000)) {
            response = "Note's title length have to be between 5 and 100 characters including" +
                    "and note's content length have to be between 5 and 10000 characters including";
        } else if (note.getTitle().length() < 5 || note.getTitle().length() > 100) {
            response = "Note's title length have to be between 5 and 100 characters including";
        } else if (note.getContent().length() < 5 || note.getContent().length() > 10000) {
            response = "Note's content length have to be between 5 and 10000 characters including";
        }
        return response;
    }

    public void copyLink(String url) {
        String linkToCopy = "https://www.example.com"; // Замініть на ваше посилання

        // Створюємо об'єкт StringSelection з текстом, який ми хочемо скопіювати
        StringSelection stringSelection = new StringSelection(url);

        System.setProperty("java.awt.headless", "false");

        // Отримуємо об'єкт Clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Встановлюємо об'єкт StringSelection як вміст буфера обміну
        clipboard.setContents(stringSelection, null);
    }

}
