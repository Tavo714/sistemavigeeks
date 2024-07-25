package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.pcds2.grupo3.sistemavigeeks.models.Role;
import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
            .map(Role::getNombre)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
	}

}
