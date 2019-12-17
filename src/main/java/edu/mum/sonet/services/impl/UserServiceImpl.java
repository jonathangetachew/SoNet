package edu.mum.sonet.services.impl;

import edu.mum.sonet.security.JwtTokenProvider;
import edu.mum.sonet.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.UserRepository;
import edu.mum.sonet.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	private AuthenticationManager authenticationManager;

	private FileService fileService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
	                       @Lazy JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, FileService fileService) {
		super(userRepository);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
		this.fileService = fileService;
	}

	@Override
	public String login(String email, String password) {

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			String token = jwtTokenProvider.createToken(authentication);
			return token;
		} catch (AuthenticationException e) {
//			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User register(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public User saveProfileChanges(User user, String imagesDirectory) {
		User response = null;
		User originalUser = findByEmail(user.getEmail());
		if (passwordEncoder.matches(user.getOldPassword(), originalUser.getPassword())) {
			originalUser.setName(user.getName());
			if (user.getPassword() != null && user.getPassword().length() > 3) {
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				originalUser.setPassword(encodedPassword);
			}
			originalUser.setEmail(user.getEmail());
			originalUser.setGender(user.getGender());
			originalUser.setDateOfBirth(user.getDateOfBirth());
			originalUser.setLocation(user.getLocation());
			if (user.getImageFile() != null) {
				String imagePath = fileService.saveFile(user.getImageFile(), imagesDirectory);
				imagePath = imagePath.substring(imagePath.lastIndexOf("profileImages/"));
				if (imagePath != null) {
					fileService.deleteFile(originalUser.getImageUrl());
					originalUser.setImageUrl("/" + imagePath);
				}
			}
			response = save(originalUser);
		}
		return response;
	}

	@Override
	public Boolean isAuthenticatedUserFollowUser(String authenticatedEmail, User targetUser) {
		return userRepository.existsByEmailAndFollowingUsers(authenticatedEmail, targetUser);
	}

	@Override
	public void follow(String authenticatedEmail, String targetUserEmail) {
		User targetUser = findByEmail(targetUserEmail);
		User authenticatedUser = userRepository.getUserByEmailFetchFollowers(authenticatedEmail);

		///> Following the user adds that user in the following users list
		authenticatedUser.addFollowingUser(targetUser);

		userRepository.save(authenticatedUser);
	}

	@Override
	public void unfollow(String authenticatedEmail, String targetUserEmail) {
		User targetUser = findByEmail(targetUserEmail);
		User authenticatedUser = userRepository.getUserByEmailFetchFollowers(authenticatedEmail);

		///> UnFollowing a user removes that user from the following users list
		authenticatedUser.removeFollowingUser(targetUser);

		userRepository.save(authenticatedUser);
	}
}
