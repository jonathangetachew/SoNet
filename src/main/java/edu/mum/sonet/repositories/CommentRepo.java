package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
