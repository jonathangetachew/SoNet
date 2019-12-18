package edu.mum.sonet.controllers.rest;

import edu.mum.sonet.exceptions.ResourceNotFoundException;
import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.repositories.CommentRepository;
import edu.mum.sonet.repositories.PostRepository;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.UserNotificationService;
import edu.mum.sonet.services.UserService;
import edu.mum.sonet.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostRestController {
    public static final int DEFAULT_PAGE_SIZE = 5;

    private final PostRepository postRepository;
    private final UserService userService;
    private final FileService fileService;
    private final UserNotificationService userNotificationService;
    private final CommentRepository commentRepository;

    @Autowired
    public PostRestController(PostRepository postRepository, CommentRepository commentRepository, UserService userService, FileService fileService, UserNotificationService userNotificationService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.commentRepository = commentRepository;
        this.userNotificationService = userNotificationService;
    }

    private User getCurrentUser() throws UsernameNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    private String getUrl(Post post, HttpServletRequest request) {
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        MultipartFile file = post.getContentFile();
        String result = null;
        if (file != null) {
            String contentUrl = fileService.saveFile(file, rootDirectory + "post/");
            contentUrl = contentUrl.substring(contentUrl.lastIndexOf("post/"));
            result = "/" + contentUrl;
        } else {
            List<String> extractedUrls = StringUtils.extractUrls(post.getText());

            if (!extractedUrls.isEmpty()) result = extractedUrls.get(0);
        }

        return result;
    }

    @PostMapping(value = "/api/v1/user/post", consumes = {"multipart/form-data"})
    public Post save(@Valid Post post, @RequestParam("notifyFollowers") Boolean notifyFollowers, HttpServletRequest
            request) {
        String result = getUrl(post, request);
        if (result != null) post.setContentUrl(result);

        User currentUser = getCurrentUser();
        currentUser.addPost(post);

        if (notifyFollowers) {
            System.out.println(" currentUser ->> followers: " + currentUser.getFollowers());
            UserNotification un = new UserNotification();
            un.setPost(post);
            un.setUsers(currentUser.getFollowers());
            userNotificationService.notifyUser(un);
        }
        return postRepository.save(post);
    }

    @PutMapping(value = "/api/v1/user/post/{id}", consumes = {"multipart/form-data"})
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest, HttpServletRequest
            request) {
        return postRepository.findById(postId).map(post -> {
            String result = getUrl(post, request);
            if (result != null) post.setContentUrl(result);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @GetMapping(value = "/api/v1/user/posts")
    public Map<String, Object> posts(@RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size) {
        int currentPage = (page == null || page < 1) ? 1 : page;
        int pageSize = (size == null || size < 0) ? DEFAULT_PAGE_SIZE : size;

        Map<String, Object> map = new HashMap<>();
        Page<Post> resultPage = postRepository.loadMorePostIsHealthy(getCurrentUser().getId(), PageRequest.of(currentPage - 1, pageSize, Sort.by("creation_date").descending()));
        boolean hasMore = currentPage * pageSize < resultPage.getTotalElements();
        map.put("data", resultPage.toSet());
        map.put("nextPage", hasMore ? currentPage + 1 : currentPage);
        map.put("hasMore", hasMore);

        return map;
    }

    @GetMapping(value = "/api/v1/user/post/{id}")
    public Post getSinglePost(@PathVariable("id") Long id) {
        return postRepository.findById(id).get();
    }

    @PostMapping("/api/v1/user/post/{id}/comment")
    public Comment addComment(@PathVariable Long id, @Valid Comment comment) {
        return postRepository.findById(id).map(post -> {
            comment.setAuthor(getCurrentUser());
            comment.setId(null);
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
    }

    @GetMapping("/api/v1/user/post/{id}/comments")
    @ResponseBody
    public Map<String, Object> getPostComments(@PathVariable Long id,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {
        int currentPage = (page == null || page < 1) ? 1 : page;
        int pageSize = (size == null || size < 0) ? DEFAULT_PAGE_SIZE : size;

        Map<String, Object> map = new HashMap<>();
        Page<Comment> resultPage = commentRepository.findByPostIdAndIsHealthyOrderByCreationDateDesc(id, true, PageRequest.of(currentPage - 1, pageSize));
        map.put("data", resultPage.toSet());

        boolean hasMore = currentPage * pageSize < resultPage.getTotalElements();
        map.put("nextPage", hasMore ? currentPage + 1 : currentPage);
        map.put("hasMore", hasMore);
        return map;
    }
}
