package com.blogging.controllers;

import com.blogging.config.AppConstants;
import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.services.FileService;
import com.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    //	Get posts by user
    @GetMapping("/user/{uId}/posts")
    public ResponseEntity <List<PostDto>> getPostByUser(@PathVariable Integer uId) {
        List<PostDto>  posts=this.postService.getPostByUser(uId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    // Get by category
    @GetMapping("/category/{cId}/posts")
    public ResponseEntity <List<PostDto>> getPostByCategory(@PathVariable Integer cId) {
        List<PostDto>  posts=this.postService.getPostByCategory(cId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    // get all posts
    /* Without Pagination
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPost() {
        List<PostDto> allPost = this.postService.getAllPost();
        return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
    }
    */

    /*
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize)
    {
        List<PostDto> allPost = this.postService.getAllPost(pageNumber, pageSize);
        return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
    }
    */

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
    {
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    // get post by id
    @GetMapping("/posts/{pId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer pId) {
        PostDto postDto=this.postService.getPostById(pId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    // Update post
    @PutMapping("/posts/{pId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer pId) {
        PostDto pDto=this.postService.updatePost(postDto, pId);
        return new ResponseEntity<PostDto>(pDto, HttpStatus.OK);
    }

    //	Delete posts
    @DeleteMapping("/posts/{pId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer pId) {
        this.postService.deletePost( pId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted successfully", true),
                HttpStatus.OK);
    }

    // search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDto> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    //	Download image
    @GetMapping(value = "post/image/{imgName}", produces= MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(
            @PathVariable ("imgName") String imageName,
            HttpServletResponse response) throws  IOException{

        InputStream res = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        //StreamUtils.copy(res, response, getOutputStream() );
    }
}
