package com.saber.person.soap.api.authentication;

import com.saber.person.soap.api.entity.Users;
import com.saber.person.soap.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomBasicUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public CustomBasicUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optionalUsers = this.userRepository.findByUsername(username);
		if (optionalUsers.isEmpty())
			throw new UsernameNotFoundException(String.format("user with username %s not found", username));
		return optionalUsers.get();
	}
}
