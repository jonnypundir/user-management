package com.pundir.usermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
	private String token;
	private String username;
	private String type = "Bearer";
	private String id;
	private String email;
	private List<String> roles;
}
