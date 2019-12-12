package edu.mum.sonet.bootstrap;

import edu.mum.sonet.models.*;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.repositories.*;
import edu.mum.sonet.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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

	private final AdvertisementRepository advertisementRepository;

	private final ClaimRepository claimRepository;

	private final CommentRepository commentRepository;

	private final NotificationRepository notificationRepository;

	private final PostRepository postRepository;

	private final UserService userService;

	public SonetBootstrap(AdvertisementRepository advertisementRepository, ClaimRepository claimRepository,
	                      CommentRepository commentRepository, NotificationRepository notificationRepository,
	                      PostRepository postRepository, UserService userService) {
		this.advertisementRepository = advertisementRepository;
		this.claimRepository = claimRepository;
		this.commentRepository = commentRepository;
		this.notificationRepository = notificationRepository;
		this.postRepository = postRepository;
		this.userService = userService;
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

		postRepository.saveAll(Arrays.asList(post, post2));

		///> Add Notifications
		Notification notification = new Notification();
		notification.setIsSeen(false);
		notification.setPost(post);

		Notification notification2 = new Notification();
		notification2.setIsSeen(true);
		notification2.setPost(post2);

		notificationRepository.saveAll(Arrays.asList(notification, notification2));

		User user1 = new User();
		user1.setName("Mahmoud");
		user1.setLocation("Fairfield");
		user1.setGender(Gender.MALE);
		user1.setEmail("mahmoud@gmail.com");
		user1.setPassword("m");
		user1.setImageUrl("/tmp/tomcat-docbase.13474198551271694409.8080/profileImages/images/test case (Failing test)-1576046052.png");

		userService.register(user1);
	}
}
