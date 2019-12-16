package edu.mum.sonet.repositories;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTextContainingIgnoreCase(String searchInput, Pageable pageable);

    List<Post> findAllByIsHealthyAndIsDisabled(Boolean isHealthy, Boolean isDisabled);

    @Query(value = "select c from Post p join p.comments c where p.id = :id and c.isHealthy = true")
    Page<Comment> loadMoreCommentsByPostIdIsHealthyOrderById(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT P.* FROM POST P INNER JOIN (SELECT ID FROM USER WHERE ID = :id OR ID IN (SELECT FOLLOWING_USERS_ID FROM USER_FOLLOWING_USERS WHERE USER_ID = :id)) U ON P.AUTHOR_ID = U.ID WHERE P.IS_HEALTHY = TRUE ORDER BY P.CREATION_DATE DESC", nativeQuery = true)
    Page<Post> loadMorePostIsHealthy(@Param("id") Long id, Pageable pageable);
}
