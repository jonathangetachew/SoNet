package edu.mum.sonet.bootstrap;

import edu.mum.sonet.models.*;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Role;
import edu.mum.sonet.repositories.*;
import edu.mum.sonet.services.UserService;
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
		advertisement.setTargetLocation("Fairfield, IA");
		advertisement.setTargetAge(20);
		advertisement.setTargetGender(Gender.NONE);

		Advertisement advertisement2 = new Advertisement();
		advertisement2.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fmedium.com%2F%40RyanJosephHill%2Fwhy-spotify-is-one-of-my-favorite-products-93fa4dff850a&psig=AOvVaw2TjV5sAqmD9xn9bGPO0Q6q&ust=1575430150604000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOiq2-bEmOYCFQAAAAAdAAAAABAD");
		advertisement2.setText("Spotify");
		advertisement2.setAdUrl("https://www.spotify.com/");
		advertisement2.setTargetLocation("San Fransisco, CA");
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

		///> Add Claims
		Claim claim = new Claim();
		claim.setClaimDate(LocalDate.now());
		claim.setMessage("My post was taken down for no reason!!! :(");
		claim.setIsAccepted(false);

		Claim claim2 = new Claim();
		claim2.setClaimDate(LocalDate.now().minusDays(1));
		claim2.setMessage("This isn't fair ;(");
		claim2.setIsAccepted(false);

		claimRepository.saveAll(Arrays.asList(claim, claim2));

		///> Add Posts
		Post post = new Post();
		post.setText("Amazing Day");
		post.setContentUrl("https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.coldplay.com%2Fwatch-the-amazing-day-film%2F&psig=AOvVaw17sr46xbfFBae1hb2NgGdf&ust=1575430915730000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJiK1dPHmOYCFQAAAAAdAAAAABAD");
		post.setLikeCount(100);
		post.setCommentCount(2);
		post.setIsHealthy(true);
		post.addComment(comment);
		post.addComment(comment2);

		Post post2 = new Post();
		post2.setText("Enterprise Architecture Course Rocks!");
		post2.setContentUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cbsnews.com%2Fpictures%2Fevolution-of-the-starship-enterprise%2F&psig=AOvVaw3PMmNXbZK0R6iadeOJjrs9&ust=1575432100340000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIjWr4jMmOYCFQAAAAAdAAAAABAJ");
		post2.setLikeCount(1000);
		post2.setCommentCount(1);
		post2.setIsHealthy(false);
		post2.addComment(comment3);

		///> Add Users
		User user = new User();
		user.setName("Admin");
		user.setEmail("admin@sonet.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setImageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiZqOS1qK7mAhWpGDQIHVgsDFEQjRx6BAgBEAQ&url=http%3A%2F%2Fwww.iconarchive.com%2Fshow%2Ffree-large-boss-icons-by-aha-soft%2Fadmin-icon.html&psig=AOvVaw24tJMsiKmpscIQBzUEqU30&ust=1576178438949212");
		user.setGender(Gender.FEMALE);
		user.setLocation("Fairfield, IA");
		user.setDateOfBirth(LocalDate.of(2000, 1, 1));
		user.setRole(Role.ADMIN);

		User user2 = new User();
		user2.setName("User");
		user2.setEmail("user@sonet.com");
		user2.setPassword(passwordEncoder.encode("user"));
		user2.setImageUrl("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjirsGzqa7mAhVzGDQIHfijBQAQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fuser%2Bicon&psig=AOvVaw1yGp9HJM6KGjbphW9mAxYv&ust=1576178700805099");
		user2.setGender(Gender.OTHER);
		user2.setLocation("San Francisco, CA");
		user2.setDateOfBirth(LocalDate.of(2005, 1, 1));
		user2.addPost(post);
		user2.addPost(post2);
		user2.addClaim(claim);
		user2.addClaim(claim2);

		User user3 = new User();
		user3.setName("User2");
		user3.setEmail("user2@sonet.com");
		user3.setPassword(passwordEncoder.encode("user2"));
		user3.setImageUrl("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjirsGzqa7mAhVzGDQIHfijBQAQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fuser%2Bicon&psig=AOvVaw1yGp9HJM6KGjbphW9mAxYv&ust=1576178700805099");
		user3.setGender(Gender.OTHER);
		user3.setLocation("Fairfield");
		user3.setDateOfBirth(LocalDate.of(2005, 1, 1));


		userRepository.saveAll(Arrays.asList(user, user2, user3));

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
