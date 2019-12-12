package edu.mum.sonet.controllers.rest;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.services.PostService;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class PostController {

    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/post")
    public Post save(@Valid @RequestBody Post post) {
        String email = "user@sonet.com";//SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email);
        user.addPost(post);

        return postService.save(post);
    }

    @GetMapping(value = "/posts")
    public Set<Post> posts() {
        String email = "user@sonet.com";//SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email);
        return user.getPosts();
    }
}
