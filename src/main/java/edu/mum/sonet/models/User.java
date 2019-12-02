package edu.mum.sonet.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

	private String name;
	private String email;
	private String password;
	private String imageUrl;
	private Gender gender;
	private String location;
	private LocalDate dateOfBirth;
	private Boolean blocked; 
	private Role role;
	
	@OneToMany(mappedBy = "user", targetEntity = Post.class)
	@JsonIgnoreProperties(value = "user")
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "user", targetEntity = Claim.class)
	private Set<Claim> claims;
	
}
