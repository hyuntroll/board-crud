package pong.ios.boardcrud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.CustomUserDetails;
import pong.ios.boardcrud.repository.UserRepository;

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
