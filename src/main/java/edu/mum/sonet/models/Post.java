package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

	@Lob
	@NotEmpty
	private String text;

	@Lob
	private String contentUrl;
	private Integer likeCount;
	private Integer commentCount;
	private Boolean isHealthy;
	private LocalDate creationDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = {"posts"})
	private User author;

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
