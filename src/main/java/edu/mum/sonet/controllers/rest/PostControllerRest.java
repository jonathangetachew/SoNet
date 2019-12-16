package edu.mum.sonet.controllers.rest;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.PostService;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class PostControllerRest {

    private final PostService postService;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public PostControllerRest(PostService postService, UserService userService, FileService fileService) {
        this.postService = postService;
        this.userService = userService;
        this.fileService = fileService;
    }

    private User getCurrentUser() throws UsernameNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @RequestMapping(value = "/user/post", method = {RequestMethod.POST, RequestMethod.PUT},
            consumes = {"multipart/form-data"})
    public Post save(@Valid Post post, HttpServletRequest request) {
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        if(post.getContentFile() != null) {
            String contentUrl = fileService.saveFile(post.getContentFile(), rootDirectory + "post/");
            contentUrl = contentUrl.substring(contentUrl.lastIndexOf("post/"));
            post.setContentUrl("/" + contentUrl);
        }

        getCurrentUser().addPost(post);
        return postService.save(post);
    }

    @GetMapping(value = "/user/posts")
    public Set<Post> posts() {
        return getCurrentUser().getPosts();
    }
}
