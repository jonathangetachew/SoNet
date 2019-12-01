package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long>{

}
