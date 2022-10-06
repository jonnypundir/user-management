package com.pundir.usermanagement.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class AddressRequest {

    @ApiModelProperty(example = "21/22, Abc, Xyz...")
    private String address;

    @ApiModelProperty(example = "Pune", required = true)
    @NotBlank
    private String city;

    @ApiModelProperty(example = "Maharashtra", required = true)
    @NotBlank
    private String state;

    @ApiModelProperty(example = "123456", required = true)
    @NotBlank
    @Size(max = 40)
    private int pin;

    @ApiModelProperty(example = "1234567890", required = true)
    @NotBlank
    @Size(max = 10)
    private Long contact;
}
