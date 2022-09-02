package com.blogging.services;

import com.blogging.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer pId);
    void deleteComment(Integer cId);
}
