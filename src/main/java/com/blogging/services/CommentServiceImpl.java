package com.blogging.services;

import com.blogging.entities.Comment;
import com.blogging.entities.Post;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CommentDto;
import com.blogging.repositories.CommentRepository;
import com.blogging.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer pId) {   // pId => post id
        Post post= this.postRepository.findById(pId).orElseThrow(()->
                new ResourceNotFoundException("Post", "Post id", pId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment =  this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer cId) {    // cId => comment id
        Comment comment = this.commentRepository.findById(cId).orElseThrow(()->
                new ResourceNotFoundException("Comment", "CommentId", cId));
        this.commentRepository.delete(comment);
    }
}
