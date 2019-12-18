package edu.mum.sonet.services;

import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.models.UserNotificationJoin;

import java.util.List;
import java.util.Set;

public interface UserNotificationService extends GenericService<UserNotification>{

    void notifyUser(UserNotification userNotification, Set<User> usersToNotify);

    List<UserNotification> findAllOrderByIdDesc();

    List<UserNotificationJoin> getUserNotifications(String email);

    UserNotification saveUserNotification(UserNotification userNotification);
}
