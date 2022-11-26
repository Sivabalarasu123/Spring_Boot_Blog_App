package com.springboot.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto 
{
	private Long id;
	
	@NotEmpty
	@Size(min = 2,message = "Post title should contain atleast 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10,message = "Post description should contain atleast 10 characters")
	private String description;
	
	
	private String content;
	
	private Set<CommentDto> comments;
}
