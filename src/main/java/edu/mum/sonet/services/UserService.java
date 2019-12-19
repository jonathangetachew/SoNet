package edu.mum.sonet.services;

import edu.mum.sonet.models.User;

public interface UserService extends GenericService<User>{

    String login(String email, String password);
    User register(User user);
    User findByEmail(String email);
    User saveProfileChanges(User user,String imagesDirectory);
    Boolean isAuthenticatedUserFollowUser(String authenticatedEmail,User user);
    void follow(String authenticatedEmail, String targetUserEmail);
    void unfollow(String authenticatedEmail, String targetUserEmail);
}
