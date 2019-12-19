package edu.mum.sonet.repositories;

import edu.mum.sonet.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByIsHealthyAndIsDisabled(Boolean isHealthy, Boolean isDisabled);

	Page<Comment> findByPostIdAndIsHealthyOrderByCreationDateDesc(Long postId, Boolean isHealthy, Pageable pageable);

	Integer countAllByPostIdAndIsHealthy(Long postId, Boolean isHealthy);

	Optional<Comment> findByIdAndPostId(Long id, Long postId);
}
