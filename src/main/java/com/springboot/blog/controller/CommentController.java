package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController 
{
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId,@RequestBody CommentDto commentDto)
	{
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getAllCommentByPostId(@PathVariable("postId") Long postId)
	{
		return commentService.getCommentsByPostId(postId);	
	}
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId)
	{
		return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId,@RequestBody CommentDto commentDto)
	{
		return new ResponseEntity<CommentDto>(commentService.updateComment(postId, commentId,commentDto),HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId,@PathVariable Long commentId)
	{
		commentService.DeleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment Deleted Successfully",HttpStatus.OK);
	}
}
