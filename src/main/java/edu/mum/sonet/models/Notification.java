package edu.mum.sonet.models;

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
public class Notification extends BaseEntity {

	private Boolean isSeen;
	
	@ManyToMany
	private Set<User> users;
	
	@OneToOne
	@JoinColumn(name = "post_id")
	private Post post;
}
