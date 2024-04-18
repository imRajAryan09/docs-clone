package com.google.docs.service.user;

import com.google.docs.entity.UserEntity;
import com.google.docs.exception.ResourceNotFoundException;
import com.google.docs.repository.UserRepository;
import com.google.docs.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(String id) {
        UserEntity user = userRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
