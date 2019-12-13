package edu.mum.sonet.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.mum.sonet.models.enums.AuthProvider;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude={"posts", "claims"})
public class User extends BaseEntity {

	private String name;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String location;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private Integer unhealthyContentCount = 0;

	private Boolean blocked = false;

	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@Transient
	@JsonIgnore
	private MultipartFile imageFile;

	@Transient
	private String oldPassword;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider= AuthProvider.LOCAL;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST ,targetEntity = Post.class)
	@JsonIgnoreProperties(value = "user")
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", targetEntity = Claim.class)
	private Set<Claim> claims = new HashSet<>();

	@OneToMany
	private Set<User> followers = new HashSet<>();

	@OneToMany
	private Set<User> followingUsers = new HashSet<>();

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
