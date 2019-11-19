package com.cyio.backend.security;

import com.cyio.backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/*
    this class provides data to the CostumUserDetailsService
    TODO: Implement username in this class as well
 */

public class UserPrincipal implements OAuth2User, UserDetails {
    private String id;
    private String email;
    private String password;
    private boolean admin = false;
    private Collection<? extends GrantedAuthority> authorities; //list of access the user has
    private Map<String, Object> attributes;

    public  UserPrincipal (String id, String email, String password, Boolean admin, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.admin = admin;
    }

    public static UserPrincipal create(User user){


        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//        if (user.isAdmin()){
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
        return new UserPrincipal(
                user.getUserid(),
                user.getEmail(),
                user.getPassword(),
                user.isAdmin(),
                authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes){
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
