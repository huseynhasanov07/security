package com.example.security.ingress.service;

import com.example.security.ingress.dao.model.CategoryEntity;
import com.example.security.ingress.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryHistoryServiceHandler {
    private final CategoryRepository categoryRepository;

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void save(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "categoryById", key = "#id"),
            @CacheEvict(cacheNames = "categories", allEntries = true)
    })
    public void update(Long id, String name) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow();
        categoryEntity.setName(name);
        categoryRepository.save(categoryEntity);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "categoryById", key = "#id"),
            @CacheEvict(cacheNames = "categories", allEntries = true)
    })
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "categories")
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Cacheable(cacheNames = "categoryById", key = "#id")
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
