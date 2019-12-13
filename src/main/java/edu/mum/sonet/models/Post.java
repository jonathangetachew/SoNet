package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
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
	private Integer likeCount = 0;
	private Integer commentCount = 0;
	private Boolean isHealthy = true;

	@CreationTimestamp
	private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "author_id")
	@JsonIgnoreProperties(value = {"posts"})
	private User author;

	@Transient
	@JsonIgnore
	private MultipartFile contentFile;

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
