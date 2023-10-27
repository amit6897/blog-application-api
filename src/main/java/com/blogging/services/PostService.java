package com.blogging.services;

import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {
    // create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // update
    PostDto updatePost (PostDto postDto, Integer postId);

    // delete
    void deletePost(Integer postId);

    // get Single Post
    PostDto getPostById(Integer postId);

    // get All Posts
    //List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get all posts by category
    List<PostDto> getPostByCategory(Integer catId);

    // get all posts by user
    List<PostDto> getPostByUser(Integer userId);

    // search posts
    List<PostDto> searchPosts(String keyword);
}
