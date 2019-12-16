package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.repositories.UserNotificationRepository;
import edu.mum.sonet.services.UserNotificationService;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserNotificationServiceImpl extends GenericServiceImpl<UserNotification> implements UserNotificationService {

	private UserNotificationRepository userNotificationRepository;
	private SimpMessagingTemplate template;

	@Autowired
	public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository) {
		super(userNotificationRepository);
		this.userNotificationRepository = userNotificationRepository;
	}

	@Override
	public void notifyUser(UserNotification userNotification) {
		userNotificationRepository.save(userNotification);
		Set<User> usersToNotify = userNotification.getUsers();
		for (User u : usersToNotify){
			template.convertAndSend("/user/"+u.getEmail());
		}

	}

	@Override
	public List<UserNotification> findAllOrderByIdDesc() {
		return userNotificationRepository.findAllByOrderByIdDesc();
	}
}
