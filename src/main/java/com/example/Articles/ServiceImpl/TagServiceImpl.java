package com.example.Articles.ServiceImpl;

import com.example.Articles.entity.Article;
import com.example.Articles.entity.Tag;
import com.example.Articles.interfaces.TagService;
import com.example.Articles.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // сортироватка  по алфавиту
    @Override
    public List<Tag> getAllTagsSorted() {
        List<Tag> tags = tagRepository.findAll();
        tags.sort(Comparator.comparing(Tag::getName, String.CASE_INSENSITIVE_ORDER));
        return tags;
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        // Разрываем связь тега со всеми статьями
        for (Article article : tag.getArticles()) {
            article.getTags().remove(tag);
        }

        tagRepository.delete(tag);
    }

}
