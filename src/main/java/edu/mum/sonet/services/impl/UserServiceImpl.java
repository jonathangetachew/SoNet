package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.UserRepository;
import edu.mum.sonet.services.UserService;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository repo, UserRepository userRepository) {
		super(repo);
		this.userRepository = userRepository;
	}

}
