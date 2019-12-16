package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "searchInput",required = false) String searchInput,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "size", required = false) Integer size,Model model){
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(1);
        int currentPage = (page == null)?1:page;
        int pageSize = (size == null)?1:size;
        System.out.println(">>>> search: "+searchInput);
        if(searchInput != null) {
            Page<Post> postPage = postService.search(searchInput, PageRequest.of(currentPage - 1, pageSize));
//            if (posts == null) {
//                posts = new ArrayList<>();
//            }
            System.out.println(" search result: "+ postPage.get().count());
            model.addAttribute("postPage",postPage);
            int totalPages = postPage.getTotalPages();
            System.out.println(">>> toltalpage: "+totalPages);
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                System.out.println(">>>> pageNumbers: "+pageNumbers);
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("searchInput", searchInput);
            }
        }

        return "/user/search";
    }
}
