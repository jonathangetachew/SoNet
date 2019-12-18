package edu.mum.sonet.bootstrap;

import edu.mum.sonet.models.*;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Location;
import edu.mum.sonet.models.enums.Role;
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
 * Loads Bootstrap data into the database on startup
 */
@Component
public class SonetBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final UserRepository userRepository;

	private final AdvertisementRepository advertisementRepository;

	private final ClaimRepository claimRepository;

	private final UserNotificationRepository notificationRepository;

	private final UnhealthyWordRepository unhealthyWordRepository;

	private final PasswordEncoder passwordEncoder;
	private final CommentRepository commentRepository;

	public SonetBootstrap(UserRepository userRepository, AdvertisementRepository advertisementRepository,
						  ClaimRepository claimRepository, UserNotificationRepository notificationRepository,
						  UnhealthyWordRepository unhealthyWordRepository, PasswordEncoder passwordEncoder, CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.advertisementRepository = advertisementRepository;
		this.claimRepository = claimRepository;
		this.notificationRepository = notificationRepository;
		this.unhealthyWordRepository = unhealthyWordRepository;
		this.passwordEncoder = passwordEncoder;
		this.commentRepository = commentRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (advertisementRepository.findAll().isEmpty()) loadData();
	}

	private void loadData() {
		///> Add Advertisements
		Advertisement advertisement = new Advertisement();
		advertisement.setContentUrl("https://images-eu.ssl-images-amazon.com/images/I/411j1k1u9yL.png");
		advertisement.setText("Amazon Prime Video");
		advertisement.setAdUrl("https://www.amazon.com/Prime-Video/");
		advertisement.setLocation(Location.FAIRFIELD);
		advertisement.setTargetAge(20);
		advertisement.setTargetGender(Gender.NONE);

		Advertisement advertisement2 = new Advertisement();
		advertisement2.setContentUrl("https://i.pinimg.com/originals/6a/ae/a1/6aaea1a2f9296fe9fb44bbad0431acad.png");
		advertisement2.setText("Spotify");
		advertisement2.setAdUrl("https://www.spotify.com/");
		advertisement2.setLocation(Location.SAN_FRANCISCO);
		advertisement2.setTargetAge(18);
		advertisement2.setTargetGender(Gender.NONE);

		advertisementRepository.saveAll(Arrays.asList(advertisement, advertisement2));

		///> Add Posts
		Post post = new Post();
		post.setText("Amazing Day");
		post.setContentUrl("https://s.ftcdn.net/v2013/pics/all/curated/RKyaEDwp8J7JKeZWQPuOVWvkUjGQfpCx_cover_580.jpg");
		post.setLikeCount(100);
		post.setCommentCount(2);
		post.setIsHealthy(true);

		Post post2 = new Post();
		post2.setText("Beautiful Scenery");
		post2.setContentUrl("https://i.pinimg.com/originals/cc/18/8c/cc188c604e58cffd36e1d183c7198d21.jpg");
		post2.setLikeCount(1000);
		post2.setCommentCount(1);
		post2.setIsHealthy(true);

		Post post3 = new Post();
		post3.setText("What an amazing course it's been 😊😊");
		post3.setContentUrl("https://i.ytimg.com/vi/3Q8u7nKKSiY/maxresdefault.jpg");
		post3.setLikeCount(1000);
		post3.setCommentCount(0);
		post3.setIsHealthy(true);

		Post post4 = new Post();
		post4.setText("I'm a bad post having words like crap and shit. I dare you to remove me! 😜");
		post4.setContentUrl("https://i.ytimg.com/vi/3Q8u7nKKSiY/maxresdefault.jpg");
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
		user.setLocation(Location.FAIRFIELD);
		user.setDateOfBirth(LocalDate.of(2000, 1, 1));
		user.setRole(Role.ADMIN);

		User user2 = new User();
		user2.setName("User");
		user2.setEmail("user@sonet.com");
		user2.setPassword(passwordEncoder.encode("user123"));
		user2.setImageUrl("https://semantic-ui.com/images/avatar2/large/matthew.png");
		user2.setGender(Gender.OTHER);
		user2.setLocation(Location.ADDIS_ABABA);
		user2.setDateOfBirth(LocalDate.of(2005, 1, 1));
		user2.addPost(post);
		user2.addPost(post2);

		User user3 = new User();
		user3.setName("User2");
		user3.setEmail("user2@sonet.com");
		user3.setPassword(passwordEncoder.encode("user21"));
		user3.setImageUrl("http://www.newsshare.in/wp-content/uploads/2017/04/Miniclip-8-Ball-Pool-Avatar-16.png");
		user3.setGender(Gender.OTHER);
		user3.setLocation(Location.CAIRO);
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
		user4.setLocation(Location.HAVANA);
		user4.setDateOfBirth(LocalDate.of(1995, 6, 12));
		user4.setUserStatus(UserStatus.BANNED);

		User nastyUser = new User();
		nastyUser.setName("Nasty User");
		nastyUser.setEmail("nastyuser@sonet.com");
		nastyUser.setPassword(passwordEncoder.encode("user"));
		nastyUser.setImageUrl("https://semantic-ui.com/images/avatar2/large/matthew.png");
		nastyUser.setGender(Gender.OTHER);
		nastyUser.setLocation(Location.SAN_FRANCISCO);
		nastyUser.setDateOfBirth(LocalDate.of(2005, 1, 1));

		userRepository.saveAll(Arrays.asList(user, user2, user3, user4, nastyUser));

		///> Add Comments
		Comment comment = new Comment();
		comment.setText("Hello World");
		comment.setIsHealthy(true);
		comment.setAuthor(user2);
		comment.setPost(post);

		Comment comment2 = new Comment();
		comment2.setText("Miss Xing is an Amazing YouTube Channel!");
		comment2.setIsHealthy(true);
		comment2.setAuthor(user3);
		comment2.setPost(post);

		Comment comment3 = new Comment();
		comment3.setText("I'm a bad word.");
		comment3.setIsHealthy(false);
		comment3.setAuthor(nastyUser);
		comment3.setPost(post2);

		commentRepository.saveAll(Arrays.asList(comment, comment2, comment3));

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
		UserNotification notification = new UserNotification();
		notification.setIsSeen(false);
		notification.setPost(post);

		UserNotification notification2 = new UserNotification();
		notification2.setIsSeen(true);
		notification2.setPost(post2);

		notificationRepository.saveAll(Arrays.asList(notification, notification2));

		///> Add Unhealthy Words
		UnhealthyWord unhealthyWord = new UnhealthyWord();
		unhealthyWord.setWord("bad");
		UnhealthyWord unhealthyWord2 = new UnhealthyWord();
		unhealthyWord2.setWord("crap");
		UnhealthyWord unhealthyWord3 = new UnhealthyWord();
		unhealthyWord3.setWord("fuck");
		UnhealthyWord unhealthyWord4 = new UnhealthyWord();
		unhealthyWord4.setWord("kill");
		UnhealthyWord unhealthyWord5 = new UnhealthyWord();
		unhealthyWord5.setWord("shit");

		unhealthyWordRepository.saveAll(Arrays.asList(unhealthyWord, unhealthyWord2, unhealthyWord3,
				unhealthyWord4, unhealthyWord5));
	}
}
