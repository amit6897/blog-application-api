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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto,(HttpStatus.CREATED));
    }

    // update USER
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto CategoryDto, @PathVariable Integer id) {
        CategoryDto updatedCategory  = this.categoryService.updateCategory(CategoryDto, id);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }

    //	delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        this.categoryService.deleteCategory( id);
		//return ResponseEntity.ok(Map.of("Message", "User Deleted successfully"));
        return new ResponseEntity<ApiResponse>(new ApiResponse
                ("Deleted successfully", true), HttpStatus.OK);
    }

    //	Get users
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
        return ResponseEntity.ok(this.categoryService.getCategoryById(id));
    }
}
