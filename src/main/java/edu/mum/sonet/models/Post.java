package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
//@EqualsAndHashCode(exclude = {"contentFile", "author","comments"})
public class Post extends BaseEntity {

	@Lob
	@NotEmpty
	private String text;

	@Lob
	private String contentUrl;
  
	private Integer likeCount = 0;
	private Integer commentCount = 0;
	private Boolean isHealthy = true;
	private Boolean isDisabled = false;

	@CreationTimestamp
	private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "author_id")
	@JsonIgnoreProperties(value = {"posts"})
	private User author;

	@Transient
	@JsonIgnore
	private MultipartFile contentFile;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "post_comment",
			joinColumns = {@JoinColumn(name = "post_id")},
			inverseJoinColumns = {@JoinColumn(name = "comment_id")}
	)
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	@JsonIgnore
	private Set<Comment> comments = new HashSet<>();

	public boolean addComment(Comment comment) {
		if (comments.add(comment)) return true;
		return false;
	}

	public boolean removeComment(Comment comment) {
		if (comments.remove(comment)) return true;
		return false;
	}


	@Override
	public String toString() {
		return "Post{" +
				"text='" + text + '\'' +
				", contentUrl='" + contentUrl + '\'' +
				", likeCount=" + likeCount +
				", commentCount=" + commentCount +
				", isHealthy=" + isHealthy +
				", isDisabled=" + isDisabled +
				'}';
	}
}
