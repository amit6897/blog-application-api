package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CommentDto;
import com.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{pId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer pId) {
        CommentDto createCommentDto = this.commentService.createComment(commentDto, pId);
        return new ResponseEntity<CommentDto>(createCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer cId) {
        this.commentService.deleteComment(cId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted comment successfully", true), HttpStatus.OK);
    }
}
