package com.blogging.services;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.repositories.CategoryRepository;
import com.blogging.repositories.PostRepository;
import com.blogging.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", "User id", userId));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepository.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException( "Post", " id ", postId));
        Category category = this.categoryRepository.findById(postDto.getCategory().getCategoryId()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);

        Post updatedPost = this.postRepository.save(post);
        // PostDto postDtos = this.modelMapper.map(updatedPost, PostDto.class);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException( "Post", "post id ", postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "Post id", postId));
        // PostDto postDtos = this.modelMapper.map(post, PostDto.class);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        /**
         * Sort sort = null;
         * if (sortDir.equalsIgnoreCase("asc")) {
         *      sort = Sort.by(sortBy).ascending();
         * } else {
         *      sort = Sort.by(sortBy).descending();
         * }
         */

        Pageable pag = PageRequest.of(pageNumber, pageSize, sort);
        Page <Post> pagePost = this.postRepository.findAll(pag);
        List <Post> allPosts = pagePost.getContent();

        List <PostDto> postDtos = allPosts.stream().map((p) ->
                this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public List<PostDto> getPostByCategory(Integer catId) {
        Category category = this.categoryRepository.findById(catId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category id", catId));
        List <Post> posts = this.postRepository.findByCategory(category);

        List <PostDto> postDtos =  posts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", "User id", userId));

        List <Post> posts = this.postRepository.findByUser(user);
        List <PostDto> postDtos =  posts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.searchByTitle("%" + keyword + "%");
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    /**
     * @Override
     * public List<PostDto> searchPosts(String keyword) {
     *      List<Post> posts = this.postRepository.findByTitleContaining(keyword);
     *      List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
     *      return postDtos;
     * }
     */

    /*
    @Override
    public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize) {
         Without Pagination Code
        List<Post> allPosts = this.postRepository.findAll();
        List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;


        Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List <PostDto> postDtos = pagePost.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }
    */

}
