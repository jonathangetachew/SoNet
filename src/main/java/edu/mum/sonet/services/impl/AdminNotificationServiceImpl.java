package edu.mum.sonet.services.impl;

//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    @Value("javainuse.exchange")
//    private String exchange;
//
////    @Value("${javainuse.rabbitmq.routingkey}")
//@Value("javainuse.routingkey")
//    private String routingkey;


    @Autowired
    public AdminNotificationServiceImpl(AdminNotificationRepository adminNotificationRepository,SimpMessagingTemplate template) {
        super(adminNotificationRepository);
        this.adminNotificationRepository = adminNotificationRepository;
        this.template = template;
//        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void notifyAdmin(AdminNotification adminNotification) {
        template.convertAndSend("/notifications/admin",adminNotification);
        adminNotificationRepository.save(adminNotification);
        System.out.println(">>>> notifyAdmin with: "+adminNotification.getType());

//        rabbitTemplate.convertAndSend("/topic/public", adminNotification);
    }

    @Override
    public List<AdminNotification> findAllOrderByIdDesc() {
        return adminNotificationRepository.findAllByOrderByIdDesc();
    }
}

