package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.CommentRepository;
import edu.mum.sonet.repositories.PostRepository;
import edu.mum.sonet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService {

	private final CommentRepository commentRepository;
	private PostRepository postRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
		super(postRepository);
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public Page<Post> loadMorePosts(User current, Pageable pageable) {
		return postRepository.loadMorePostIsHealthy(current.getId(), pageable);
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
		System.out.println(">>> save a Post <<<<");
		return super.save(entity);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public Page<Post> loadMorePostIsHealthy(Long id, Pageable pageable) {
		return postRepository.loadMorePostIsHealthy(id,pageable);
	}
}
