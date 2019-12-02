package edu.mum.sonet.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.repositories.CommentRepo;
import edu.mum.sonet.services.CommentService;

@Service
@Transactional
public class CommentServiceImpl extends GenericServiceImpl<Comment> implements CommentService {

	private CommentRepo commentRepo;

	@Autowired
	public CommentServiceImpl(CommentRepo repo, CommentRepo commentRepo) {
		super(repo);
		this.commentRepo = commentRepo;

	}

}
