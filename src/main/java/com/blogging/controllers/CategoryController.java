package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CategoryDto;
import com.blogging.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory,(HttpStatus.CREATED));
    }

    // update USER
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto CategoryDto, @PathVariable Integer catId) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(CategoryDto, catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }

    // delete User
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer catId) {
        this.categoryService.deleteCategory(catId);
		//return ResponseEntity.ok(Map.of("Message", "User Deleted successfully"));
        return new ResponseEntity<ApiResponse>(new ApiResponse
                ("Deleted successfully", true), HttpStatus.OK);
    }

    // get all users
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // get user by category id
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }
}
