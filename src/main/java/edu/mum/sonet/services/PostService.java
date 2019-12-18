package edu.mum.sonet.services;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostService extends GenericService<Post> {

    Page<Post> loadMorePosts(User current, Pageable pageable);

    Page<Post> search(String searchInput, Pageable pageable);

    List<Post> findAllUnhealthyPosts();

    Post save(Post post);

    Optional<Post> findById(Long id);

    Page<Post> loadMorePostIsHealthy(Long id, Pageable pageable);
}
