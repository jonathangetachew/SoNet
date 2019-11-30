package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Post;

public interface PostRepo extends JpaRepository<Post, Long>{

}
