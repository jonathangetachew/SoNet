package edu.mum.sonet.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

		ADMIN,USER,NONE;

	@Override
	public String getAuthority() {
		return name();
	}
}
