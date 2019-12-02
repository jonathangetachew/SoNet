package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Notification;
import edu.mum.sonet.repositories.NotificationRepo;
import edu.mum.sonet.services.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl extends GenericServiceImpl<Notification> implements NotificationService {

	private NotificationRepo notificationRepo;

	@Autowired
	public NotificationServiceImpl(NotificationRepo repo, NotificationRepo notificationRepo) {
		super(repo);
		this.notificationRepo = notificationRepo;
	}

}
