package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

	@Lob
	@NotEmpty
	private String text;
	private Boolean isHealthy = true;
	private Boolean isDisabled = false;

	@ManyToOne
	@JsonIgnoreProperties(value = {"authProvider", "posts", "oldPassword", "claims", "followers", "following", "unhealthyContentCount"})
	private User author;

	@CreationTimestamp
	private LocalDateTime creationDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Post post;
}
