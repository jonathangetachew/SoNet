package edu.mum.sonet.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

	private String text;
	private String contentUrl;
	private Integer likeCount;
	private Integer commentCount;
	private Boolean isHealthy;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value= {"posts"})
	private User user;
	
	@OneToMany
	private Set<Comment> comments;
}
