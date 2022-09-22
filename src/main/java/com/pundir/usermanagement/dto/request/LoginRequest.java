package com.pundir.usermanagement.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@Builder
@ApiModel
public class LoginRequest {
	@ApiModelProperty(example = "abc@gmail.com", required = true)
	@NotBlank
	private String email;

	@ApiModelProperty(example = "Xyz#123", required = true)
	@NotBlank
	private String password;
}
