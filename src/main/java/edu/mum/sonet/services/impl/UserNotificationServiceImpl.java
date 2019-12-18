package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.UserNotificationJoin;
import edu.mum.sonet.repositories.UserNotificationJoinRepository;
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
	private UserNotificationJoinRepository userNotificationJoinRepository;
	private SimpMessagingTemplate template;

	@Autowired
	public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository, SimpMessagingTemplate template,
									   UserNotificationJoinRepository userNotificationJoinRepository) {
		super(userNotificationRepository);
		this.userNotificationRepository = userNotificationRepository;
		this.template = template;
		this.userNotificationJoinRepository = userNotificationJoinRepository;
	}

	@Override
	public void notifyUser(UserNotification userNotification,Set<User> usersToNotify) {
		try {
//			System.out.println(" >>> userNotification:  notify: " + userNotification.getUsers().size());
//			Post originalPost = userNotification.getPost();
//			userNotification.getPost().getAuthor().setFollowers(new HashSet<>());
//			userNotification.getPost().getAuthor().setFollowing(new HashSet<>());


//			if (userNotification.getUsers().size() > 0) {
			if (usersToNotify.size() > 0) {
				UserNotification un =saveUserNotification(userNotification);
//				userNotificationRepository.save(userNotification);
//				Set<User> usersToNotify = userNotification.getUsers();
				for (User u : usersToNotify) {
					UserNotificationJoin unj = new UserNotificationJoin();
					unj.setUser(u);
					unj.setUserNotification(un);
					saveUserNotificationJoin(unj);
					System.out.println(">>> push notification for : " + u.getEmail());
//					System.out.println(">>> push post: " + originalPost.getText());
//				template.convertAndSend("/notifications/" + u.getEmail());
//					UserNotification un = userNotification;
					template.convertAndSend("/notifications/"+u.getEmail(), userNotification);
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
	public List<UserNotificationJoin> getUserNotifications(String email) {
//		return userNotificationRepository.findUserNotificationForUser(email);
		return userNotificationJoinRepository.getAllByUser_Email(email);
	}

	@Override
	public UserNotification saveUserNotification(UserNotification userNotification) {
		return userNotificationRepository.save(userNotification);
	}

//	@Override
	public UserNotificationJoin saveUserNotificationJoin(UserNotificationJoin userNotificationJoin) {
		return userNotificationJoinRepository.save(userNotificationJoin);
	}
}
