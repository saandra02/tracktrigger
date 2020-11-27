package com.example.tracktrigger.configuration;

import com.example.tracktrigger.security.AuthenticationFilter;
import com.example.tracktrigger.security.AuthorizationFilter;
import com.example.tracktrigger.services.ApplicationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.example.tracktrigger.constants.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private ApplicationUserDetailsService userDetailsService;

    public SecurityConfiguration(ApplicationUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        		.antMatchers("/", "/js/**", "/css/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers("/verify-registration").permitAll()
                .antMatchers(HttpMethod.POST, "/demo/test").permitAll()
                .antMatchers(HttpMethod.GET, "/demo/all").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/userlogin").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/verify").permitAll()
                .antMatchers("/profile").permitAll()
                .antMatchers("/categories").permitAll()
                .antMatchers("/inventory").permitAll()
                .antMatchers("/log").permitAll()
                .antMatchers("/scheduleEmail").permitAll()
                .antMatchers("/numberverification").permitAll()
                .antMatchers("/dashboard").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


}
