package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
}
