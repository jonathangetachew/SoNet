package edu.mum.sonet.controllers.rest;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/post")
    public Post save(@Valid @RequestBody Post post) {
        post.setCreationDate(LocalDate.now());

        return postService.save(post);
    }

    @GetMapping(value = "/posts")
    public List<Post> posts() {
        return Arrays.asList(new Post(), new Post());
    }
}
