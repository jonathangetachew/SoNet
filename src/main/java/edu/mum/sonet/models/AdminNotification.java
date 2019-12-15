package edu.mum.sonet.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class AdminNotification extends BaseEntity {

    private String type;
    private String text;
    @ManyToOne
    private User user;


    public AdminNotification(String type, String text, User user) {
        this.type = type;
        this.text = text;
        this.user = user;
    }
}
