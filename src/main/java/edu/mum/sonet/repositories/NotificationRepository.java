package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
}
