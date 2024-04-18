package com.google.docs.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public interface IUserService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(String id);
}
