package com.Hcl.Authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
    private boolean enabled;
}
