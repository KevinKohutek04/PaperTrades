package PaperWeight.Biscuit_corpo.service;

import PaperWeight.Biscuit_corpo.entity.ERole;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        UserDetailsImpl userDet = new UserDetailsImpl(user.getUsername(), user.getPassword());
        //userDet.setEnabled(user.getActive()==0?false:true); wtf is this
        List<GrantedAuthority> authorities =  new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ERole.
                ROLE_USER.toString()));
        userDet.setAuthorities(authorities);

        return userDet;
    }
}
/*
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

}
* */