package edu.mum.sonet.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findByTextContainingIgnoreCase(String searchInput, Pageable pageable);
	List<Post> findAllByIsHealthyAndIsDisabled(Boolean isHealthy, Boolean isDisabled);
}
