package edu.mum.sonet.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

	@Lob
	private String text;

	@Lob
	private String contentUrl;
	private Integer likeCount;
	private Integer commentCount;
	private Boolean isHealthy = true;
	private Boolean isDisabled = false;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value= {"posts"})
	private User user;
	
	@OneToMany
	@JoinTable(name = "post_comment",
			joinColumns = {@JoinColumn(name = "post_id")},
			inverseJoinColumns = {@JoinColumn(name = "comment_id")}
	)
	private Set<Comment> comments = new HashSet<>();

	public boolean addComment(Comment comment) {
		if (comments.add(comment)) return true;
		return false;
	}

	public boolean removeComment(Comment comment) {
		if (comments.remove(comment)) return true;
		return false;
	}
}
