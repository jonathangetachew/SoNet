package edu.mum.sonet.repositories;

import edu.mum.sonet.models.UserNotificationJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationJoinRepository extends JpaRepository<UserNotificationJoin, Long> {
    List<UserNotificationJoin> getAllByUser_Email(String email);
}
