package edu.mum.sonet.services;

import edu.mum.sonet.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService extends GenericService<Comment>{
	List<Comment> findAllUnhealthyComments();
	Comment save(Comment comment);
	Page<Comment> findByPostIdAndIsHealthyOrderByCreationDateDesc(Long postId, Boolean isHealthy, Pageable pageable);
}
