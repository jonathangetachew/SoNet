package edu.mum.sonet.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.mum.sonet.models.enums.AuthProvider;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

	private String name;

	@Column(unique = true)
	private String email;
	private String password;
	private String imageUrl;
	private Gender gender;
	private String location;
	private LocalDate dateOfBirth;
	private Boolean blocked; 
	private Role role = Role.USER;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider= AuthProvider.LOCAL;

	private String providerId;

	@OneToMany(mappedBy = "user", targetEntity = Post.class)
	@JsonIgnoreProperties(value = "user")
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", targetEntity = Claim.class)
	private Set<Claim> claims = new HashSet<>();

	/**
	 *
	 * Added custom add and remove methods to handle relationships
	 *
	 * @param post
	 * @return
	 */
	public boolean addPost(Post post) {
		if (posts.add(post)) {
			post.setUser(this);
			return true;
		}
		return false;
	}

	public boolean removePost(Post post) {
		if (posts.remove(post)) {
			post.setUser(null);
			return true;
		}
		return false;
	}

	public boolean addClaim(Claim claim) {
		if (claims.add(claim)) {
			claim.setUser(this);
			return true;
		}
		return false;
	}

	public boolean removeClaim(Claim claim) {
		if (claims.remove(claim)) {
			claim.setUser(null);
			return true;
		}
		return false;
	}
	
}
