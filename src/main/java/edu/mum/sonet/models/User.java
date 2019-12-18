package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.mum.sonet.models.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"posts", "claims","followers","following"})
public class User extends BaseEntity {

	@NotBlank
	private String name;

	@Email
	@NotBlank
	@Column(unique = true)
	private String email;

	@NotBlank
	@JsonIgnore
	private String password;

	private String imageUrl;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.NONE;

	@Enumerated(EnumType.STRING)
	private Location location;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private Integer unhealthyContentCount = 0;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus = UserStatus.ACTIVE;

	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@Transient
	@JsonIgnore
	private MultipartFile imageFile;

	@Transient
	private String oldPassword;

	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider = AuthProvider.LOCAL;

	@OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, targetEntity = Post.class, fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = "author")
	private Set<Post> posts = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "follow_record",
			joinColumns = {@JoinColumn(name = "follower", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "followed", nullable = false)}
	)
	@JsonIgnoreProperties(value = {"following","followers"})
	private Set<User> following = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "follow_record",
			joinColumns = {@JoinColumn(name = "followed", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "follower", nullable = false)}
	)
	@JsonIgnoreProperties(value = {"following","followers"})
	private Set<User> followers = new HashSet<>();

	/**
	 * Added custom add and remove methods to handle relationships
	 *
	 * @param post
	 * @return
	 */
	public boolean addPost(Post post) {
		if (posts.add(post)) {
			post.setAuthor(this);
			return true;
		}
		return false;
	}

	public boolean removePost(Post post) {
		if (posts.remove(post)) {
			post.setAuthor(null);
			return true;
		}
		return false;
	}

	public boolean follow(User user) {
		if (following.add(user)) {
			return true;
		}
		return false;
	}

	public boolean unfollow(User user) {
		if (following.remove(user)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", gender=" + gender +
				", location=" + location +
				", dateOfBirth=" + dateOfBirth +
				", unhealthyContentCount=" + unhealthyContentCount +
				", userStatus=" + userStatus +
				", role=" + role +
				'}';
	}
}
