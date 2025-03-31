package com.example.Articles.interfaces;

import com.example.Articles.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag createTag(Tag tag);
    void deleteTag(Long id);
}
