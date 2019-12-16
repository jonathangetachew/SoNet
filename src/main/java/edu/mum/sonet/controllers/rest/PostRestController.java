package edu.mum.sonet.controllers.rest;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.PostService;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PostRestController {
    public static final int DEFAULT_PAGE_SIZE = 5;

    private final PostService postService;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public PostRestController(PostService postService, UserService userService, FileService fileService) {
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

    @RequestMapping(value = "/api/v1/user/post", method = {RequestMethod.POST, RequestMethod.PUT},
            consumes = {"multipart/form-data"})
    public Post save(@Valid Post post, HttpServletRequest request) {
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        MultipartFile file = post.getContentFile();
        if (file != null) {
            String contentUrl = fileService.saveFile(file, rootDirectory + "post/");
            contentUrl = contentUrl.substring(contentUrl.lastIndexOf("post/"));
            post.setContentUrl("/" + contentUrl);
        }
        getCurrentUser().addPost(post);
        return postService.save(post);
    }

    @GetMapping(value = "/api/v1/user/posts")
    public Map<String, Object> posts(@RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size) {
        int currentPage = (page == null) ? 1 : page < 0 ? 1 : page;
        int pageSize = (size == null) ? DEFAULT_PAGE_SIZE : size < 0 ? 1 : size;

        Page<Post> resultPage = postService.loadMorePosts(getCurrentUser(), PageRequest.of(currentPage - 1, pageSize));
        Map<String, Object> map = new HashMap<>();
        map.put("nextPage", currentPage + 1);
        map.put("data", resultPage.toSet());

        return map;
    }

    @GetMapping(value = "/api/v1/user/post/{id}")
    public Post getSinglePost(@PathVariable("id") Long id) {
        Post p = postService.findById(id);
//        p.setComments((Set<Comment>) getPostComments(p.getId(), 1, 5).get("data"));
        return p;
    }

    @PostMapping("/api/v1/user/post/{id}/comment")
    public Comment addComment(@PathVariable Long id, @Valid Comment comment) {
        return postService.addComment(id, getCurrentUser(), comment);
    }

    @GetMapping("/api/v1/user/post/{id}/comments")
    @ResponseBody
    public Map<String, Object> getPostComments(@PathVariable Long id,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {
        int currentPage = (page == null) ? 1 : page < 0 ? 1 : page;
        int pageSize = (size == null) ? DEFAULT_PAGE_SIZE : size < 0 ? 1 : size;
        int pageCount = postService.findById(id).getComments().size();
        //if (pageCount > 0 && currentPage > pageCount) {
        //   currentPage = pageCount;
        // }

        Page<Comment> resultPage = postService.loadMoreHealthyCommentsFromPost(id, PageRequest.of(currentPage - 1, pageSize));
        Map<String, Object> map = new HashMap<>();
        map.put("nextPage", currentPage + 1);
        map.put("data", resultPage.toSet());
        return map;
    }
}
