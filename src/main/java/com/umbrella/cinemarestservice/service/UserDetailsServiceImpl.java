package com.umbrella.cinemarestservice.service;

import com.umbrella.cinemarestservice.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository
                .findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }
}
