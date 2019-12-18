package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.repositories.UserNotificationRepository;
import edu.mum.sonet.services.UserNotificationService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserNotificationServiceImpl extends GenericServiceImpl<UserNotification> implements UserNotificationService {

	private UserNotificationRepository userNotificationRepository;
	private SimpMessagingTemplate template;

	@Autowired
	public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository, SimpMessagingTemplate template) {
		super(userNotificationRepository);
		this.userNotificationRepository = userNotificationRepository;
		this.template = template;
	}

	@Override
	public void notifyUser(UserNotification userNotification) {
		try {
			System.out.println(" >>> userNotification:  notify: " + userNotification.getUsers().size());
			Post originalPost = userNotification.getPost();
			originalPost.getAuthor().setFollowers(new HashSet<>());
			originalPost.getAuthor().setFollowing(new HashSet<>());


			if (userNotification.getUsers().size() > 0) {
				userNotificationRepository.save(userNotification);
				Set<User> usersToNotify = userNotification.getUsers();
				for (User u : usersToNotify) {
					System.out.println(">>> push notification for : " + u.getEmail());
					System.out.println(">>> push post: " + originalPost.getText());
//				template.convertAndSend("/notifications/" + u.getEmail());
					template.convertAndSend("/notifications", originalPost.getText());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<UserNotification> findAllOrderByIdDesc() {
		return userNotificationRepository.findAllByOrderByIdDesc();
	}

	@Override
	public List<UserNotification> getUserNotifications(String email) {
		return userNotificationRepository.findUserNotificationForUser(email);
	}
}
