package com.blogging.services;

import com.blogging.entities.Category;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CategoryDto;
import com.blogging.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat = this.categoryRepository.save(category);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
        Category category = this.categoryRepository.findById(catId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", catId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category cat = this.categoryRepository.findById(catId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", catId));
        this.categoryRepository.delete(cat);
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category cat  = this.categoryRepository.findById(catId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", catId));
        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List <Category> categories = this.categoryRepository.findAll();
        List <CategoryDto> list = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).
                collect(Collectors.toList());
        return list;
    }
}
