package com.example.jpa.jpaonetomanydemo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;	

import com.example.jpa.jpaonetomanydemo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	
	Optional<Comment> findByIdAndPostId(Long id, Long postId);
	
	 Page<Comment> findByPostId(Long postId, Pageable pageable);
}
