package com.brickfarm.auth;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.support.ConnectionFactoryRegistry;
//import org.springframework.social.google.connect.GoogleConnectionFactory;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests()
//		.antMatchers("/", "/login**", "/callback/", "/webjars/**").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.oauth2Login()
//		.loginPage("/login")
//		.userInfoEndpoint()
//		.userService(new CustomOAuth2UserService());
//	}
//	
//    @Bean
//    public ConnectionFactoryLocator connectionFactoryLocator() {
//        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//        registry.addConnectionFactory(new GoogleConnectionFactory("238176025376-mq77vel7fjv7rgj2tal19gcodt7u7n49.apps.googleusercontent.com", "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"));
//        return registry;
//    }
//	
//}
