package com.example.Articles.interfaces;

import com.example.Articles.entity.Tag;
import com.example.Articles.entity.User;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> getAllTagsSorted();
    Tag createTag(Tag tag);
    void deleteTag(Long id);
}
