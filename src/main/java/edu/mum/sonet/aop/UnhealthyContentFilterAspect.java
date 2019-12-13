package edu.mum.sonet.aop;

import edu.mum.sonet.exceptions.UnhealthyContentDetectedException;
import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.services.CommentService;
import edu.mum.sonet.services.PostService;
import edu.mum.sonet.services.UnhealthyContentFilterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Aspect
@Component
public class UnhealthyContentFilterAspect {

	private final UnhealthyContentFilterService unhealthyContentFilterService;

	public UnhealthyContentFilterAspect(UnhealthyContentFilterService unhealthyContentFilterService) {
		this.unhealthyContentFilterService = unhealthyContentFilterService;
	}

	@Around(value = "execution(* edu.mum.sonet.services.CommentService.save(..)) " +
			"&& execution(* edu.mum.sonet.services.PostService.save(..))")
	public Object filter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		boolean isUnhealthy = false;

		Object[] args = proceedingJoinPoint.getArgs();

		if ( proceedingJoinPoint.getTarget() instanceof CommentService) {
			Comment comment = (Comment) args[0];

			if (unhealthyContentFilterService.hasUnhealthyContent(comment.getText())) {
				comment.setIsHealthy(false);
				isUnhealthy = true;
				///> Change the argument
				args[0] = comment;
			}
		} else if (proceedingJoinPoint.getTarget() instanceof PostService) {
			Post post = (Post) args[0];

			if (unhealthyContentFilterService.hasUnhealthyContent(post.getText())) {
				post.setIsHealthy(false);
				isUnhealthy = true;
				///> Change the argument
				args[0] = post;
			}
		}


		Object ret = proceedingJoinPoint.proceed(args);

		if (isUnhealthy) throw new UnhealthyContentDetectedException("Unhealthy Post Detected");

		return ret;
	}
}
