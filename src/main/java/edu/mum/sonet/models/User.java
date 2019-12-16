package edu.mum.sonet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.mum.sonet.models.enums.AuthProvider;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Role;
import edu.mum.sonet.models.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"posts", "claims","SSSfollowers","followingUsers"})
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
	private Gender gender;

	private String location;

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

	@OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, targetEntity = Post.class,fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = "author")
	private Set<Post> posts = new HashSet<>();

	@OneToMany
	private Set<Comment> comments = new HashSet<>();

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

	public boolean addComment(Comment comment) {
		if (comments.add(comment)) return true;
		return false;
	}

	public boolean removeComment(Comment comment) {
		if (comments.remove(comment)) return true;
		return false;
	}

	public boolean addFollower(User user) {
		if (followers.add(user)) {
			return true;
		}
		return false;
	}

	public boolean removeFollower(User user) {
		if (followers.remove(user)) {
//			user.removeFollowingUser(this);
			return true;
		}
		return false;
	}

	public boolean addFollowingUser(User user) {
		if (followingUsers.add(user)) {
			user.addFollower(this);
			return true;
		}
		return false;
	}

	public boolean removeFollowingUser(User user) {
		if (followingUsers.remove(user)) {
			user.removeFollower(this);
			return true;
		}
		return false;
	}
	
}
