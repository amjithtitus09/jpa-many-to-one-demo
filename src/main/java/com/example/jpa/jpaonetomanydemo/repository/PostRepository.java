package com.example.jpa.jpaonetomanydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.jpaonetomanydemo.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>  {

}
