package edu.mum.sonet.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class UserNotificationJoin extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private UserNotification userNotification;

}
