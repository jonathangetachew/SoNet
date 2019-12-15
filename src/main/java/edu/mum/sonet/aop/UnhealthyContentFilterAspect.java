package edu.mum.sonet.aop;

import edu.mum.sonet.exceptions.UnhealthyContentDetectedException;
import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.services.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Aspect
@Component
public class UnhealthyContentFilterAspect {

	private final UnhealthyContentFilterService unhealthyContentFilterService;

	private final UserService userService;

	private AdminNotificationService adminNotificationService;

	public UnhealthyContentFilterAspect(UnhealthyContentFilterService unhealthyContentFilterService, UserService userService
	,AdminNotificationService adminNotificationService) {
		this.unhealthyContentFilterService = unhealthyContentFilterService;
		this.userService = userService;
		this.adminNotificationService = adminNotificationService;
	}

	@Around(value = "execution(* edu.mum.sonet.services.CommentService.save(..)) " +
			"&& execution(* edu.mum.sonet.services.PostService.save(..))")
	public Object filter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		boolean isUnhealthy = false;

		AdminNotification adminNotification = null;
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userService.findByEmail(email);

		Object[] args = proceedingJoinPoint.getArgs();
		if ( proceedingJoinPoint.getTarget() instanceof CommentService) {
			Comment comment = (Comment) args[0];

			if (unhealthyContentFilterService.hasUnhealthyContent(comment.getText())) {
				comment.setIsHealthy(false);
				isUnhealthy = true;
				///> Change the argument
				args[0] = comment;
				adminNotification = new AdminNotification("Comment",comment.getText(),user);
				adminNotificationService.notifyAdmin(adminNotification);
			}
		} else if (proceedingJoinPoint.getTarget() instanceof PostService) {
			Post post = (Post) args[0];

			if (unhealthyContentFilterService.hasUnhealthyContent(post.getText())) {
				post.setIsHealthy(false);
				isUnhealthy = true;
				///> Change the argument
				args[0] = post;
				adminNotification = new AdminNotification("Post",post.getText(),user);
				adminNotificationService.notifyAdmin(adminNotification);
			}
		}

		///> Get Current User


		if ( user == null ) {
			throw new UsernameNotFoundException("Unable to find User in Principal");
		}

		///> Increment the number of unhealthy content the user has
		user.setUnhealthyContentCount(user.getUnhealthyContentCount() + 1);

		// TODO: Send Notification to admin

		if (user.getUnhealthyContentCount() >= 20 ) {
			// Disable user's account
			user.setBlocked(true);

			// TODO: Send email to user ***** DON'T FORGET TO RESET THE COUNT IF ADMIN ACCEPT'S USER'S CLAIM
		}

		Object ret = proceedingJoinPoint.proceed(args);

		if (isUnhealthy) throw new UnhealthyContentDetectedException("Unhealthy Post Detected");

		return ret;
	}
}
