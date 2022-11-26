package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService
{
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) 
	{
		Comment comment = mapToEntity(commentDto);
		
		//retrieve post entity by Id
		
		Post post = postRepository.findById(postId)
				       .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		//save comments to DB
		Comment newComment = commentRepository.save(comment);
		
		return mapToDto(newComment);
	}
	
	@Override
	public List<CommentDto> getCommentsByPostId(Long posId) 
	{
		// retrive comments by post ID
		
		List<Comment> comments = commentRepository.findByPostId(posId);
		
		return comments.stream().map(comment -> mapToDto(comment))
				                .collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		//retrieve post entity by Id
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by Id
		
		Comment comment = commentRepository.findById(commentId)
				   .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", postId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesnot belongs to the post");
		}
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
		
		//retrieve post entity by Id
		
		Post post = postRepository.findById(postId)
						.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by Id
		
		Comment comment = commentRepository.findById(commentId)
						   .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", postId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesnot belongs to the post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);	
		
		return mapToDto(updatedComment);
	}

	@Override
	public void DeleteComment(Long postId, Long commentId) 
	{
		//retrieve post entity by Id
		
        Post post = postRepository.findById(postId)
								.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
				
		// retrieve comment by Id
				
		Comment comment = commentRepository.findById(commentId)
								   .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", postId));
				
		if(!comment.getPost().getId().equals(post.getId()))
			{
				throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesnot belongs to the post");
			}
		
		commentRepository.delete(comment);
		
	}

	
	// converting Entity to DTO
	private CommentDto mapToDto(Comment comment)
	{
		CommentDto commentDto = mapper.map(comment,CommentDto.class);
		return commentDto;
		/* CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		commentDto.setName(comment.getName()); */
	}
	
	//convert Dto to Entity
	private Comment mapToEntity(CommentDto commentDto)
	{
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
		/* Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());	*/	
			
	}


}
