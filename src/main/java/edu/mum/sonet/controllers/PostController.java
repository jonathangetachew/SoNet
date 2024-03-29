package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.models.UserNotificationJoin;
import edu.mum.sonet.services.PostService;
import edu.mum.sonet.services.UserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PostController {
    private PostService postService;
    private final UserNotificationService userNotificationService;

    @Autowired
    public PostController(PostService postService, UserNotificationService userNotificationService) {
        this.postService = postService;
        this.userNotificationService = userNotificationService;
    }

    @GetMapping(value = "/user/post/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "post/details";
    }

    @GetMapping(value = "/user/posts/search")
    public String search(@RequestParam(value = "searchInput", required = false) String searchInput,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "size", required = false) Integer size, Model model) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(1);
        int currentPage = (page == null) ? 1 : page;
        int pageSize = (size == null) ? 1 : size;
        System.out.println(">>>> search: " + searchInput);
        if (searchInput != null) {
            Page<Post> postPage = postService.search(searchInput, PageRequest.of(currentPage - 1, pageSize));
//            if (posts == null) {
//                posts = new ArrayList<>();
//            }
            System.out.println(" search result: " + postPage.get().count());
            model.addAttribute("postPage", postPage);
            int totalPages = postPage.getTotalPages();
            System.out.println(">>> toltalpage: " + totalPages);
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                System.out.println(">>>> pageNumbers: " + pageNumbers);
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("searchInput", searchInput);
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        List<UserNotificationJoin> notifications = userNotificationService.getUserNotifications(currentPrincipalEmail);
        model.addAttribute("notifications",notifications);
        model.addAttribute("notificationsNumber",notifications.size());
        return "/post/search";
    }
}
