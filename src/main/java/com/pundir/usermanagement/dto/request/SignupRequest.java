package com.pundir.usermanagement.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SignupRequest {
    @ApiModelProperty(example = "Abhishek", required = true)
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @ApiModelProperty(example = "Pandey", required = true)
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @ApiModelProperty(example = "abc@gmail.com", required = true)
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @ApiModelProperty(example = "8487..", required = true)
    @NotBlank
    @Size(max = 10)
    private Long contact;

    @ApiModelProperty(example = "Abc#123..", required = true)
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
