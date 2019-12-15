package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.repositories.UserNotificationRepository;
import edu.mum.sonet.services.UserNotificationService;

@Service
@Transactional
public class UserNotificationServiceImpl extends GenericServiceImpl<UserNotification> implements UserNotificationService {

	private UserNotificationRepository userNotificationRepository;

	@Autowired
	public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository) {
		super(userNotificationRepository);
		this.userNotificationRepository = userNotificationRepository;
	}

}
