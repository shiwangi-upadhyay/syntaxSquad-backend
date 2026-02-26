package com.Hcl.Authentication.entity;

import com.Hcl.Authentication.dto.UserDetailsResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public UserDetailsResponse toDetailsResponse() {
        return new UserDetailsResponse(
                this.id,
                this.name,
                this.email,
                this.roles.stream()
                        .map(r -> r.getName().name())
                        .collect(Collectors.toList()),
                this.enabled);
    }
}
