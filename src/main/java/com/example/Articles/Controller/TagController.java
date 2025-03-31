package com.example.Articles.Controller;


import com.example.Articles.entity.Tag;
import com.example.Articles.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;


    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String getAllTags(Model model){
        List<Tag> tags = tagService.getAllTagsSorted();
        model.addAttribute("tags", tags);
        return "tags/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("tag", new Tag());
        return "tags/create";
    }

    @PostMapping
    public String createTag(@ModelAttribute Tag tag, Model model){
        try {
            tagService.createTag(tag);
            return "redirect:/tags";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "tags/create";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return "redirect:/tags";
    }
}
