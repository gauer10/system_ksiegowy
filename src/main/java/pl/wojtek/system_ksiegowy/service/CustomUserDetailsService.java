package pl.wojtek.system_ksiegowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.model.SystemUserRole;
import pl.wojtek.system_ksiegowy.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        SystemUser user = userRepository.findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException("Podany login nie istnieje");

        User userDetails = new User(user.getLogin(), user.getPassword(), user.getEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), convertAuthorities(user.getRoles()));
        return userDetails;
    }

    private Set<GrantedAuthority> convertAuthorities(Set<SystemUserRole> userRoles)
    {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (SystemUserRole  userRole : userRoles)
            authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        return authorities;
    }
}
