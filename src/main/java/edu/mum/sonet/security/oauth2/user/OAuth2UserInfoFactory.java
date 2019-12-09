package edu.mum.sonet.security.oauth2.user;

import edu.mum.sonet.exceptions.OAuth2AuthenticationProcessingException;
import edu.mum.sonet.models.enums.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
//        else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
//            return new FacebookOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
//            return new GithubOAuth2UserInfo(attributes);
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
