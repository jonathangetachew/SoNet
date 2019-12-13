package edu.mum.sonet.services;

import edu.mum.sonet.models.Comment;

import java.util.List;

public interface CommentService extends GenericService<Comment>{
	List<Comment> findAllUnhealthyComments();
}
