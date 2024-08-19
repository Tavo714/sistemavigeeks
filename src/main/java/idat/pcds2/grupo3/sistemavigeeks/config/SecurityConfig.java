package idat.pcds2.grupo3.sistemavigeeks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import idat.pcds2.grupo3.sistemavigeeks.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 private final CustomUserDetailsService customUserDetailsService;

	    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
	        this.customUserDetailsService = customUserDetailsService;
	    }

	    @Bean
	    BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    

	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	    }
	    
	    

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authorize -> authorize
	            	.requestMatchers("/users/newclient").permitAll()
	            	.requestMatchers("/orders/cli/**").hasRole("CLIENTE")
	                .requestMatchers("/public/**").permitAll()
	                .requestMatchers("/products/**").hasRole("ADMIN")
	                .requestMatchers("/orders/**").hasRole("ADMIN")
	                .requestMatchers("/categories/**").hasRole("ADMIN")
	                .requestMatchers("/users/**").permitAll()
	                .anyRequest().permitAll()
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .defaultSuccessUrl("/", true)
	                .permitAll()
	            )
	            .logout(logout -> logout
	                .permitAll()
	                .logoutSuccessUrl("/")
	            )
				.csrf(csrf-> csrf.disable());

	        return http.build();
	    }

	
	
}