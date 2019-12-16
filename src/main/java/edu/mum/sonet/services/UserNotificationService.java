package edu.mum.sonet.services;

import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.models.UserNotification;

import java.util.List;

public interface UserNotificationService extends GenericService<UserNotification>{

    void notifyUser(UserNotification adminNotification);

    List<UserNotification> findAllOrderByIdDesc();
}
