package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.PostRepository;
import edu.mum.sonet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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
	public Page<Comment> loadMoreHealthyCommentsFromPost(Long postId, Pageable pageable) {
		return postRepository.loadMoreCommentsByPostIdIsHealthyOrderById(postId, pageable);
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
		return super.save(entity);
	}

	public Comment addComment(Long postId, @Valid User current, @Valid Comment comment) {
		if (postId != null) {
			Post post = postRepository.getOne(postId);
			comment.setAuthor(current);

			Comment aux = comment;
			comment.setId(null);
			post.addComment(comment);
			postRepository.save(post);
			return aux;
		}
		return null;
	}
}
