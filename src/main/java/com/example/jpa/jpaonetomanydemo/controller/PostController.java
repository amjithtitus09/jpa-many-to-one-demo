package com.example.jpa.jpaonetomanydemo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.jpaonetomanydemo.exception.ResourceNotFoundException;
import com.example.jpa.jpaonetomanydemo.model.Post;
import com.example.jpa.jpaonetomanydemo.repository.PostRepository;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepo;

	@GetMapping("/posts")
	public Page<Post> getAllposts(Pageable pageable) {
		return postRepo.findAll(pageable);
	}

	@PostMapping("/posts")
	public Post createPost(@Valid @RequestBody Post post) {
		return postRepo.save(post);
	}

	@GetMapping("/posts/{id}")
	public Post createPost(@PathVariable(name = "id") Long id) {
		return postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
	}

	@PutMapping("/posts/{id}")
	public Post updatePost(@PathVariable(name = "id") Long id, @Valid @RequestBody Post newPost) {
		return postRepo.findById(id).map(post -> {
			post.setContent(newPost.getContent());
			post.setDescription(newPost.getDescription());
			post.setTitle(newPost.getTitle());
			return postRepo.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long id) {
		return postRepo.findById(id).map(post -> {
			postRepo.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
	}
}
