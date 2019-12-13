package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.services.CommentService;
import edu.mum.sonet.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(UnhealthyContentController.BASE_URL)
public class UnhealthyContentController {

	public static final String BASE_URL = "admin/dashboard/unhealthy-contents";

	private final PostService postService;

	private final CommentService commentService;

	public UnhealthyContentController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	/**
	 *
	 * Get Mappings
	 *
	 */

	@GetMapping(value = {"/", ""})
	public String index(Model model) {
		model.addAttribute("unhealthyPosts", postService.findAllUnhealthyPosts());
		model.addAttribute("unhealthyComments", commentService.findAllUnhealthyComments());

		return "unhealthy-content/list";
	}

	@GetMapping("/posts/{id}/allow")
	public String allowPost(@PathVariable @Valid Long id) {
		Post post = postService.findById(id);

		post.setIsHealthy(true);

		postService.save(post);

		return "redirect:/" + UnhealthyContentController.BASE_URL;
	}

	@GetMapping("/posts/{id}/disable")
	public String disablePost(@PathVariable @Valid Long id) {
		Post post = postService.findById(id);

		post.setIsDisabled(true);

		postService.save(post);

		return "redirect:/" + UnhealthyContentController.BASE_URL;
	}

	@GetMapping("/comments/{id}/allow")
	public String allowComment(@PathVariable @Valid Long id) {
		Comment comment = commentService.findById(id);

		comment.setIsHealthy(true);

		commentService.save(comment);

		return "redirect:/" + UnhealthyContentController.BASE_URL;
	}

	@GetMapping("/comments/{id}/disable")
	public String disableComment(@PathVariable @Valid Long id) {
		Comment comment = commentService.findById(id);

		comment.setIsDisabled(true);

		commentService.save(comment);

		return "redirect:/" + UnhealthyContentController.BASE_URL;
	}

}
