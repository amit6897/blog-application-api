package com.blogging.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private Integer categoryId;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 5 , message ="Title must have atleast 5 characters")
    private String categoryTitle;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 5 , message ="Description must have atleast 5 characters")
    private String categoryDescription;
}
