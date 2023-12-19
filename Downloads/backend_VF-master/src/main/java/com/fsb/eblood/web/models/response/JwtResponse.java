package com.fsb.eblood.web.models.response;

import java.util.Collection;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;

	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.authorities = authorities;

	}
}