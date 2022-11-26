package com.springboot.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentDto 
{
	private Long id;
	
	@NotEmpty(message = "Name should not be empty or null")
	private String name;
	
	@NotEmpty(message = "Email should not be empty or null")
	@Email
	private String email;
	
	private String body;
}