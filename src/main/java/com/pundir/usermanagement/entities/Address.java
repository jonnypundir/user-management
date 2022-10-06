package com.pundir.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Size(max = 6)
    private int pin;

    @NotBlank
    @Size(max = 10)
    private Long contact;

}
