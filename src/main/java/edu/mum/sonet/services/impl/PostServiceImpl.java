package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.repositories.PostRepo;
import edu.mum.sonet.services.PostService;

@Service
@Transactional
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService{

	private PostRepo postRepo;
	
	@Autowired
	public PostServiceImpl(PostRepo repo, PostRepo postRepo) {
		super(repo);
		this.postRepo = postRepo;
	}

}
