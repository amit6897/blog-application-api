package com.blogging.services;

import com.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    // Create
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);

    // Delete
    void deleteCategory(Integer catId);

    // Get
    CategoryDto getCategoryById(Integer catId) ;

    // Get All
    List<CategoryDto> getCategories();
}
