package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Notification;
import edu.mum.sonet.repositories.NotificationRepository;
import edu.mum.sonet.services.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl extends GenericServiceImpl<Notification> implements NotificationService {

	private NotificationRepository notificationRepository;

	@Autowired
	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		super(notificationRepository);
		this.notificationRepository = notificationRepository;
	}

}
