package edu.mum.sonet.security;

import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserRepository userRepository;

	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationManager authenticationManager;

	public CustomAuthenticationSuccessHandler(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (response.isCommitted()) {
			return;
		}
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		Map<String, Object> attributes = oidcUser.getAttributes();
		String email = (String) attributes.get("email");

		///> Create Authentication object to create token
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, "google"));

		 SecurityContextHolder.getContext().setAuthentication(auth);

		String token = jwtTokenProvider.createToken(auth);
		String redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/user/index")
				.queryParam("token", token)
				.queryParam("loginMessage", "Login Successful")
				.build().toUriString();
//		String redirectionUrl = UriComponentsBuilder.fromUriString("/login")
//				.queryParam("email", email)
//				.queryParam("password", "google")
//				.build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
	}
}
