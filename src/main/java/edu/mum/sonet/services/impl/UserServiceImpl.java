package edu.mum.sonet.services.impl;

import edu.mum.sonet.config.JwtTokenProvider;
import edu.mum.sonet.exceptions.ResourceNotFoundException;
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

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	private AuthenticationManager authenticationManager;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
	                       @Lazy JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
		super(userRepository);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String login(String email, String password) {

		try {
			System.out.println("==== start token (login)===");
//			String encodedPassword = passwordEncoder.encode(password);

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			String token = jwtTokenProvider.createToken(authentication);
			System.out.println(">>> generated token: "+token);
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
		System.out.println("====== service findByEmail =====");

		// TODO: implement User not found exception
		return userRepository.findByEmail(email).orElse(null);
	}
}
