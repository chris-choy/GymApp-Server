package com.chris.gym.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GymUserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper ;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User u = userMapper.findByUsername(s);

        if (u != null) {

            GrantedAuthority authority1=new SimpleGrantedAuthority("USER");

            List<GrantedAuthority> authoritieslist=new ArrayList<>();
            authoritieslist.add(authority1);

            UserDetails userDetails = new UserDao(
                    u.getUsername(),
//                    new BCryptPasswordEncoder().encode(u.getPassword()),
                    u.getPassword(),
                    authoritieslist);
            return userDetails;
        } else {
//            return null;
            throw new UsernameNotFoundException(s+"Not Found");
        }

    }
}
