package edu.mum.sonet.security;

import edu.mum.sonet.models.User;
import edu.mum.sonet.models.enums.AuthProvider;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Service
public class CustomOidcUserService extends OidcUserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public CustomOidcUserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
		Map attributes = oidcUser.getAttributes();
		GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
		userInfo.setEmail((String) attributes.get("email"));
		userInfo.setId((String) attributes.get("sub"));
		userInfo.setImageUrl((String) attributes.get("picture"));
		userInfo.setName((String) attributes.get("name"));
		updateUser(userInfo);
		return oidcUser;
	}

	private void updateUser(GoogleOAuth2UserInfo userInfo) {
		User user = userRepository.findByEmail(userInfo.getEmail()).orElse(null);

		if(user == null) {
			user = new User();
		}

		user.setEmail(userInfo.getEmail());
		user.setImageUrl(userInfo.getImageUrl());
		user.setPassword(passwordEncoder.encode("google"));
		user.setName(userInfo.getName());
		user.setGender(Gender.NONE);
		user.setAuthProvider(AuthProvider.GOOGLE);
		userRepository.save(user);
	}
}
