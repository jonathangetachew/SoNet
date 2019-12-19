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
	@JsonIgnoreProperties(value = {"authProvider", "posts", "oldPassword", "claims", "followers", "following", "unhealthyContentCount"})
	private User author;

	@Transient
	@JsonIgnore
	private MultipartFile contentFile;

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
