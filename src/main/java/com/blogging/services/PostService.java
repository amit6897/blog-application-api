package com.blogging.services;

import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost (PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPostById(Integer postId);

    //List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> getPostByCategory(Integer catId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);
}
