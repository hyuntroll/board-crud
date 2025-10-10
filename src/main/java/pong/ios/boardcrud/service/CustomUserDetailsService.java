package pong.ios.boardcrud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.global.auth.domain.CustomUserDetails;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if ( userEntity != null) {
            return new CustomUserDetails(userEntity);
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
