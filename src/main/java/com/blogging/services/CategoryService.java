package com.blogging.services;

import com.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
    void deleteCategory(Integer catId);
    CategoryDto getCategoryById(Integer catId) ;
    List<CategoryDto> getCategories();
}
