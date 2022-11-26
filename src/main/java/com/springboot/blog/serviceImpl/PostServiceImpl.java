package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService
{
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public PostDto createPost(PostDto postDto) 
	{
		// convert DTO into entity 
		Post post = mapToEntity(postDto);
				
		Post newPost = postRepository.save(post); // Save them in DB- so we r converting from DTO to Entity
		
		// covert PostEntity to PostDTO
		PostDto postResponse = mapToDto(newPost);
		
		return postResponse; // -> to client, so we r converting Entity to DTO
	}

	@Override
	public List<PostDto> getAllPosts() 
	{			
		List<Post> posts = postRepository.findAll();
				
		return posts.stream()
				.map(post -> mapToDto(post))
				.collect(Collectors.toList());
	}
	
	@Override
	public PostDto getPostById(Long id) 
	{
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));		
		return mapToDto(post);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Long id) 
	{
		//get post by id from DB, if not throw exception
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		// saving the entity
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);  //converting entity to DTO
	}

	@Override
	public void deletePostById(Long id) {
		//get post by id from DB, if not throw exception
		
		Post post = postRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));		
		postRepository.delete(post);
	}

		
	
// converted Entity into DTO
	private PostDto mapToDto(Post post)
	{	
		PostDto postDto = mapper.map(post, PostDto.class);
		return postDto;
		
		/*  PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription()); */	
		
	}

	// converted DTO into Entity
	private Post mapToEntity(PostDto postDto)
	{
		Post post = mapper.map(postDto, Post.class);
		return post;
		
		/* Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription()); */
				
	}

}

