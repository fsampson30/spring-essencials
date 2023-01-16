package com.sampson.springessencials.service;

import com.sampson.springessencials.repository.DatabaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private DatabaseUserRepository databaseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(databaseUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("DatabaseUser not found"));
    }
}
