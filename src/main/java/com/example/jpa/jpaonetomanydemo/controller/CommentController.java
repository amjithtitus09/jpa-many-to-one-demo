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
import com.example.jpa.jpaonetomanydemo.model.Comment;
import com.example.jpa.jpaonetomanydemo.repository.CommentRepository;
import com.example.jpa.jpaonetomanydemo.repository.PostRepository;

@RestController
public class CommentController {

	@Autowired
	private CommentRepository commRepo;

	@Autowired
	private PostRepository postRepo;

	@GetMapping("/posts/{postId}/comments")
	public Page<Comment> getAllCommentsByPostId(@PathVariable(name = "postId") Long id, Pageable pageable) {
		return commRepo.findByPostId(id, pageable);
	}

	@PostMapping("/posts/{postId}/comments")
	public Comment createComment(@PathVariable(name = "postId") Long id, @RequestBody @Valid Comment com) {
		return postRepo.findById(id).map(post -> {
			com.setPost(post);
			return commRepo.save(com);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
	}

	@PutMapping("/posts/{postId}/comments/{comId}")
	public Comment updateComment(@PathVariable(name = "postId") Long id, @PathVariable(name = "comId") Long comId,
			@RequestBody @Valid Comment com) {
		return commRepo.findByIdAndPostId(comId, id).map(oldCom -> {
			oldCom.setText(com.getText());
			return commRepo.save(com);
		}).orElseThrow(() -> new ResourceNotFoundException("CommentId " + comId + "not found"));
	}

	@DeleteMapping("/posts/{postId}/comments/{comId}")
	public ResponseEntity<?> deleteComment(@PathVariable(name = "postId") Long id,
			@PathVariable(name = "comId") Long comId) {
		return commRepo.findByIdAndPostId(comId, id).map(oldCom -> {
			commRepo.delete(oldCom);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comId + " and postId " + id));
	}

}
