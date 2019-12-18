package edu.mum.sonet.repositories;

import edu.mum.sonet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.UserNotification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long>{

    List<UserNotification> findAllByOrderByIdDesc();

    @Query("select un from UserNotification un inner join un.users u where u.email =:email")
    List<UserNotification> findUserNotificationForUser(@Param("email") String email);
}
