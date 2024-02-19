package com.fdmgroup.DionMangaReader.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.dal.UserRepository;

@Service
public class AuthUserService implements org.springframework.security.core.userdetails.UserDetailsService{
	private UserRepository userRepository;

	@Autowired
	public AuthUserService(UserRepository userRepo) {
		super();
		this.userRepository = userRepo;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException(username));
		return new AuthUser(user);
	}
	
	

}
