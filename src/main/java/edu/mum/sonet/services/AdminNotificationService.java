package edu.mum.sonet.services;

import edu.mum.sonet.models.AdminNotification;

import java.util.List;

public interface AdminNotificationService extends GenericService<AdminNotification> {

    void notifyAdmin(AdminNotification adminNotification);

    List<AdminNotification> findAllOrderByIdDesc();
}
