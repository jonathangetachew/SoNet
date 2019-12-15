package edu.mum.sonet.advisor;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Jonathan on 12/14/2019.
 */

@ControllerAdvice
public class GlobalAdvise {

	private final UserService userService;

	public GlobalAdvise(UserService userService) {
		this.userService = userService;
	}

	/**
	 *
	 * Inject Current user information into all controller models
	 *
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@ModelAttribute("currentUser")
	private User getCurrentUser() throws UsernameNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByEmail(email);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found");
//		}
		return user;
	}

}
