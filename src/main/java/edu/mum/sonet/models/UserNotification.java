package edu.mum.sonet.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class UserNotification extends BaseEntity {

	private Boolean isSeen = false;

	@OneToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToMany
	private Set<User> users = new HashSet<>();

	public UserNotification(Post post, Set<User> users) {
		this.post = post;
		this.users = users;
	}

	public boolean addUser(User user) {
		if (users.add(user)) return true;
		return false;
	}

	public boolean removeUser(User user) {
		if (users.remove(user)) return true;
		return false;
	}
}
