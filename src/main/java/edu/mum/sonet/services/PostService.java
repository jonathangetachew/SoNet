package edu.mum.sonet.services;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostService extends GenericService<Post> {
    Page<Comment> loadMoreHealthyCommentsFromPost(Long postId, Pageable pageable);

    Page<Post> loadMorePosts(User current, Pageable pageable);

    Page<Post> search(String searchInput, Pageable pageable);

    List<Post> findAllUnhealthyPosts();

    Post save(Post post);

    Comment addComment(Long postId, User current, Comment comment);
}
