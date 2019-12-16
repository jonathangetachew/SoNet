package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.repositories.PostRepository;
import edu.mum.sonet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService {

	private PostRepository postRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		super(postRepository);
		this.postRepository = postRepository;
	}

	@Override
	public Page<Post> search(String searchInput, Pageable pageable) {
		return postRepository.findByTextContainingIgnoreCase(searchInput, pageable);
	}

	@Override
	public List<Post> findAllUnhealthyPosts() {
		return postRepository.findAllByIsHealthyAndIsDisabled(false, false);
	}

	@Override
	public Post save(Post entity) {
		return super.save(entity);
	}
}
