package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.UserRepo;
import edu.mum.sonet.services.UserService;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	private UserRepo userRepo;

	@Autowired
	public UserServiceImpl(UserRepo repo, UserRepo userRepo) {
		super(repo);
		this.userRepo = userRepo;
	}

}
