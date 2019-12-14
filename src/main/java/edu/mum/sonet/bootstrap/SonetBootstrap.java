package edu.mum.sonet.bootstrap;

import edu.mum.sonet.models.*;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Role;
import edu.mum.sonet.models.enums.TargetLocation;
import edu.mum.sonet.models.enums.UserStatus;
import edu.mum.sonet.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by Jonathan on 12/2/2019.
 */

/**
 *
 * Loads Bootstrap data into the database on startup
 *
 */
@Component
public class SonetBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final UserRepository userRepository;

	private final AdvertisementRepository advertisementRepository;

	private final ClaimRepository claimRepository;

	private final CommentRepository commentRepository;

	private final NotificationRepository notificationRepository;

	private final UnhealthyWordRepository unhealthyWordRepository;


	private final PasswordEncoder passwordEncoder;

	public SonetBootstrap(UserRepository userRepository, AdvertisementRepository advertisementRepository,
	                      ClaimRepository claimRepository, CommentRepository commentRepository,
	                      NotificationRepository notificationRepository, UnhealthyWordRepository unhealthyWordRepository,
	                      PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.advertisementRepository = advertisementRepository;
		this.claimRepository = claimRepository;
		this.commentRepository = commentRepository;
		this.notificationRepository = notificationRepository;
		this.unhealthyWordRepository = unhealthyWordRepository;
		this.passwordEncoder = passwordEncoder;

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (advertisementRepository.findAll().isEmpty()) loadData();
	}

	private void loadData() {
		///> Add Advertisements
		Advertisement advertisement = new Advertisement();
		advertisement.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.co.uk%2FAmazon-com-Amazon-Prime-Video%2Fdp%2FB00N28818A&psig=AOvVaw14IXNAXReitViuvytdxJUw&ust=1575429321572000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCLi-79rBmOYCFQAAAAAdAAAAABAD");
		advertisement.setText("Amazon Prime Video");
		advertisement.setAdUrl("https://www.amazon.com/Prime-Video/");
		advertisement.setTargetLocation(TargetLocation.FAIRFIELD);
		advertisement.setTargetAge(20);
		advertisement.setTargetGender(Gender.NONE);

		Advertisement advertisement2 = new Advertisement();
		advertisement2.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fmedium.com%2F%40RyanJosephHill%2Fwhy-spotify-is-one-of-my-favorite-products-93fa4dff850a&psig=AOvVaw2TjV5sAqmD9xn9bGPO0Q6q&ust=1575430150604000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOiq2-bEmOYCFQAAAAAdAAAAABAD");
		advertisement2.setText("Spotify");
		advertisement2.setAdUrl("https://www.spotify.com/");
		advertisement2.setTargetLocation(TargetLocation.SANFRANCISCO);
		advertisement2.setTargetAge(30);
		advertisement2.setTargetGender(Gender.NONE);

		advertisementRepository.saveAll(Arrays.asList(advertisement, advertisement2));

		///> Add Comments
		Comment comment = new Comment();
		comment.setText("Hello World");
		comment.setIsHealthy(true);

		Comment comment2 = new Comment();
		comment2.setText("Miss Xing is an Amazing YouTube Channel!");
		comment2.setIsHealthy(true);

		Comment comment3 = new Comment();
		comment3.setText("I'm a bad word.");
		comment3.setIsHealthy(false);

		commentRepository.saveAll(Arrays.asList(comment, comment2, comment3));

		///> Add Posts
		Post post = new Post();
		post.setText("Amazing Day");
		post.setContentUrl("https://s.ftcdn.net/v2013/pics/all/curated/RKyaEDwp8J7JKeZWQPuOVWvkUjGQfpCx_cover_580.jpg?r=1a0fc22192d0c808b8bb2b9bcfbf4a45b1793687");
		post.setLikeCount(100);
		post.setCommentCount(2);
		post.setIsHealthy(true);
		post.addComment(comment);
		post.addComment(comment2);

		Post post2 = new Post();
		post2.setText("Enterprise Architecture Course Rocks!");
		post2.setContentUrl("https://s.ftcdn.net/v2013/pics/all/curated/RKyaEDwp8J7JKeZWQPuOVWvkUjGQfpCx_cover_580.jpg?r=1a0fc22192d0c808b8bb2b9bcfbf4a45b1793687");
		post2.setLikeCount(1000);
		post2.setCommentCount(1);
		post2.setIsHealthy(false);
		post2.addComment(comment3);

		Post post3 = new Post();
		post3.setText("Enterprise Architecture Book!");
		post3.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cbsnews.com%2Fpictures%2Fevolution-of-the-starship-enterprise%2F&psig=AOvVaw3PMmNXbZK0R6iadeOJjrs9&ust=1575432100340000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIjWr4jMmOYCFQAAAAAdAAAAABAJ");
		post3.setLikeCount(1000);
		post3.setCommentCount(0);
		post3.setIsHealthy(false);

		Post post4 = new Post();
		post4.setText("Enterprise Architecture chapter 1!");
		post4.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cbsnews.com%2Fpictures%2Fevolution-of-the-starship-enterprise%2F&psig=AOvVaw3PMmNXbZK0R6iadeOJjrs9&ust=1575432100340000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIjWr4jMmOYCFQAAAAAdAAAAABAJ");
		post4.setLikeCount(1000);
		post4.setCommentCount(0);
		post4.setIsHealthy(false);

		///> Add Users
		User user = new User();
		user.setName("Admin");
		user.setEmail("admin@sonet.com");
		user.setPassword(passwordEncoder.encode("admin123"));
		user.setImageUrl("https://semantic-ui.com/images/avatar2/large/molly.png");
		user.setGender(Gender.FEMALE);
		user.setLocation("Fairfield, IA");
		user.setDateOfBirth(LocalDate.of(2000, 1, 1));
		user.setRole(Role.ADMIN);

		User user2 = new User();
		user2.setName("User");
		user2.setEmail("user@sonet.com");
		user2.setPassword(passwordEncoder.encode("user123"));
		user2.setImageUrl("https://semantic-ui.com/images/avatar2/large/matthew.png");
		user2.setGender(Gender.OTHER);
		user2.setLocation("San Francisco, CA");
		user2.setDateOfBirth(LocalDate.of(2005, 1, 1));
		user2.addPost(post);
		user2.addPost(post2);

		User user3 = new User();
		user3.setName("User2");
		user3.setEmail("user2@sonet.com");
		user3.setPassword(passwordEncoder.encode("user21"));
		user3.setImageUrl("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjirsGzqa7mAhVzGDQIHfijBQAQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fuser%2Bicon&psig=AOvVaw1yGp9HJM6KGjbphW9mAxYv&ust=1576178700805099");
		user3.setGender(Gender.OTHER);
		user3.setLocation("Fairfield");
		user3.setDateOfBirth(LocalDate.of(2005, 1, 1));
		user3.addPost(post3);
		user3.addPost(post4);
		user3.setUserStatus(UserStatus.BLOCKED);

		User user4 = new User();
		user4.setName("User3");
		user4.setEmail("user3@sonet.com");
		user4.setPassword(passwordEncoder.encode("user31"));
		user4.setImageUrl("https://semantic-ui.com/images/avatar2/large/elyse.png");
		user4.setGender(Gender.MALE);
		user4.setLocation("Ethiopia");
		user4.setDateOfBirth(LocalDate.of(1995,6, 12));
		user4.setUserStatus(UserStatus.BLOCKED);

		userRepository.saveAll(Arrays.asList(user, user2, user3, user4));

		///> Add Claims
		Claim claim = new Claim();
		claim.setClaimDate(LocalDate.now());
		claim.setMessage("My post was taken down for no reason!!! :(");
		claim.setUser(user3);

		Claim claim2 = new Claim();
		claim2.setClaimDate(LocalDate.now().minusDays(1));
		claim2.setMessage("This isn't fair ;(");
		claim2.setUser(user4);

		claimRepository.saveAll(Arrays.asList(claim, claim2));

		///> Add Notifications
		Notification notification = new Notification();
		notification.setIsSeen(false);
		notification.setPost(post);

		Notification notification2 = new Notification();
		notification2.setIsSeen(true);
		notification2.setPost(post2);

		notificationRepository.saveAll(Arrays.asList(notification, notification2));

		///> Add Unhealthy Words
		UnhealthyWord unhealthyWord = new UnhealthyWord();
		unhealthyWord.setWord("spring");
		UnhealthyWord unhealthyWord2 = new UnhealthyWord();
		unhealthyWord2.setWord("security");
		UnhealthyWord unhealthyWord3 = new UnhealthyWord();
		unhealthyWord3.setWord("enterprise");
		UnhealthyWord unhealthyWord4 = new UnhealthyWord();
		unhealthyWord4.setWord("kill");
		UnhealthyWord unhealthyWord5 = new UnhealthyWord();
		unhealthyWord5.setWord("shit");

		unhealthyWordRepository.saveAll(Arrays.asList(unhealthyWord, unhealthyWord2, unhealthyWord3,
				unhealthyWord4, unhealthyWord5));
	}
}
