package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by j-h.lee on 2015-12-30.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = true)
    private String encodedPassword;

    @Column(name = "LOGIN_PROVIDER", nullable = false)
    private Integer loginProvider;

    @Transient
    @JsonIgnore
    private Set<GrantedAuthority> authorities;

    // Override Method Declarations
    @Override
    @JsonIgnore
    public Set<GrantedAuthority> getAuthorities() {
        if ( this.authorities == null ) {
            this.authorities = new HashSet<>();
            this.authorities.addAll( AuthorityUtils.createAuthorityList("ROLE_USER") );
        }
        return this.authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
