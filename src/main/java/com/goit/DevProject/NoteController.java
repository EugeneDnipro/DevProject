package com.goit.DevProject;

import com.goit.DevProject.CrudServices.NoteService;
import com.goit.DevProject.Entities.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("note/list");
        result.addObject("notes", noteService.listAll().values());
        result.addObject("note", new Note());
        return result;
    }

    @PostMapping("/delete")
    public String noteDelete(@RequestParam(name = "id") long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") long id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/edit";
    }

    @PostMapping("/edit")
    public String save(@ModelAttribute Note note) {
        noteService.update(note);
        return "redirect:/note/list";
    }

    @PostMapping("/create")
    public String newNote(@ModelAttribute Note note, Model model) {
        model.addAttribute("note", note);
        noteService.add(note);
        return "redirect:/note/list";
    }
}
