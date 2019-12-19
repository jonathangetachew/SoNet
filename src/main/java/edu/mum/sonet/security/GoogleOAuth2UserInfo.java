package edu.mum.sonet.security;

import lombok.Data;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Data
public class GoogleOAuth2UserInfo {

	private String id;
	private String name;
	private String email;
	private String imageUrl;

}
