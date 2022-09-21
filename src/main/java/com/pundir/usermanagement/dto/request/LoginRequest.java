package com.pundir.usermanagement.dto.request;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@Builder
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
