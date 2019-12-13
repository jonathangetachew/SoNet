package edu.mum.sonet.services;

import edu.mum.sonet.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.util.List;


public interface PostService extends GenericService<Post>{

    Page<Post> search(String searchInput, Pageable pageable);
	List<Post> findAllUnhealthyPosts();
}
