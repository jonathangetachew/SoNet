package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.repositories.AdminNotificationRepository;
import edu.mum.sonet.services.AdminNotificationService;

import java.util.List;

@Service
@Transactional
public class AdminNotificationServiceImpl extends GenericServiceImpl<AdminNotification> implements AdminNotificationService {

    private AdminNotificationRepository adminNotificationRepository;
    private SimpMessagingTemplate template;

    @Autowired
    public AdminNotificationServiceImpl(AdminNotificationRepository adminNotificationRepository,SimpMessagingTemplate template) {
        super(adminNotificationRepository);
        this.adminNotificationRepository = adminNotificationRepository;
        this.template = template;
    }


    @Override
    public void notifyAdmin(AdminNotification adminNotification) {
        adminNotificationRepository.save(adminNotification);
        template.convertAndSend("/notifications/admin",adminNotification);
    }

    @Override
    public List<AdminNotification> findAllOrderByIdDesc() {
        return adminNotificationRepository.findAllByOrderByIdDesc();
    }
}

