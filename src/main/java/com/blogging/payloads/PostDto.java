package com.blogging.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 4 , message ="Content must have atleast 4 characters")
    private String content;

    @NotEmpty(message = "Can not be empty")
    private String title;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;
}
