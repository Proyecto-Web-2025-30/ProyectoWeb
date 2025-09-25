package edu.javeriana.process.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.javeriana.process.model.AppUser;

public class UserPrincipal implements UserDetails {
    private final AppUser user;
    public UserPrincipal(AppUser user){ this.user = user; }
    public AppUser getUser(){ return user; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    @Override public String getPassword(){ return user.getPasswordHash(); }
    @Override public String getUsername(){ return user.getEmail(); }
    @Override public boolean isAccountNonExpired(){ return true; }
    @Override public boolean isAccountNonLocked(){ return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled(){ return user.isEnabled(); }
}
