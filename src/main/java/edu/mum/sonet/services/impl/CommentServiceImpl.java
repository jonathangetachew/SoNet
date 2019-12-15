package edu.mum.sonet.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.repositories.CommentRepository;
import edu.mum.sonet.services.CommentService;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl extends GenericServiceImpl<Comment> implements CommentService {

	private CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		super(commentRepository);
		this.commentRepository = commentRepository;

	}

	@Override
	public List<Comment> findAllUnhealthyComments() {
		return commentRepository.findAllByIsHealthyAndIsDisabled(false, false);
	}

	@Override
	public Comment save(Comment entity) {
		return super.save(entity);
	}
}
