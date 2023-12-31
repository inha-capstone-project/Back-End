package com.inha.capstone.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity(name = "Users")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Application> applicationList;

    private String role;

    public User(String userId, String password, String nickname, LocalDateTime createdDate) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.role = "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for(String role : role.split(",")){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;

        /*return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
    }


    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
