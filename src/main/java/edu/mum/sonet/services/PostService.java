package edu.mum.sonet.services;

import edu.mum.sonet.models.Post;

import java.util.List;

public interface PostService extends GenericService<Post>{
	List<Post> findAllUnhealthyPosts();
}
