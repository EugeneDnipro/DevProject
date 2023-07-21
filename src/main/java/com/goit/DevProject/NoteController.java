package com.goit.DevProject;

import com.goit.DevProject.CrudServices.NoteService;
import com.goit.DevProject.Entities.Note;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("note/list");
        result.addObject("notes", noteService.listAll().values());
        result.addObject("countNotes", noteService.repository.count());
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
        model.addAttribute("accessFlag", noteService.isAccessFlag(note));
        return "note/edit";
    }

    @PostMapping("/edit")
    public String save(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        if (!noteService.validateNote(note).equals("OK")) {
            redirectAttributes.addFlashAttribute("errorText", noteService.validateNote(note));
            return "redirect:/note/error";
        }
        noteService.update(note);
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String showCreatePage(@ModelAttribute Note note) {
        return "note/create";
    }

    @PostMapping("/create")
    public String newNote(@ModelAttribute Note note, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("note", note);
        if (!noteService.validateNote(note).equals("OK")) {
            redirectAttributes.addFlashAttribute("errorText", noteService.validateNote(note));
            return "redirect:/note/error";
        }
        noteService.add(note);
        return "redirect:/note/list";
    }

    @GetMapping("/error")
    public String showErrorPage(@ModelAttribute Note note) {
        return "note/error";
    }

    @PostMapping("/share/{id}")
    public String getLink(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        String fullUrl = request.getRequestURL().toString();
        noteService.copyLink(fullUrl);
        return "redirect:/note/list";
    }

    @GetMapping("/share/{id}")
    public String shareLink(@PathVariable("id") long id, Model model) {
        Note note;
        try {
            note = noteService.getById(id);
        } catch (IllegalArgumentException ex) {
            return "note/warning";
        }
        model.addAttribute("note", note);
        model.addAttribute("accessFlag", noteService.isAccessFlag(note));
        if (noteService.isAccessFlag(note))
            return "note/warning";
        return "note/share";
    }
}
