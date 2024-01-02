package com.brickfarm.auth;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


//@Configuration
//@EnableWebSecurity
//public class OAuth2LoginConfig {
//
//	@Bean
//	public ClientRegistrationRepository clientRegistrationRepository() {
//
//		return new CustomClientRegistrationRepository();
//	}
//	
//	@Bean
//	public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(){
//		return new DefaultOAuth2UserService();
//	}
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return username -> {
//			if("yeumsh95@gmail.com".equals(username)) {
//				return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_MEMBER"));
//			}else {
//				throw new UsernameNotFoundException("User not found");
//			}
//		};
//	}
//	
//
//	private ClientRegistration googleClientRegistration() {
//		return ClientRegistration.withRegistrationId("google")
//				.clientId("238176025376-mq77vel7fjv7rgj2tal19gcodt7u7n49.apps.googleusercontent.com")
//				.clientSecret("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")
//				.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
//				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//				.redirectUriTemplate("http://localhost:8081/auth/google/callback")
//				.scope("https://www.googleapis.com/auth/userinfo.profile"
//						+ "https://www.googleapis.com/auth/user.addresses.read"
//						+ "https://www.googleapis.com/auth/user.birthday.read"
//						+ "https://www.googleapis.com/auth/user.emails.read"
//						+ "https://www.googleapis.com/auth/user.gender.read"
//						+ "https://www.googleapis.com/auth/user.phonenumbers.read")
//				.authorizationUri("https://accounts.google.com/o/oauth2/auth")
//				.tokenUri("https://accounts.google.com/o/oauth2/token")
//				.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo").userNameAttributeName("email") // 사용자 식별용.
//				.clientName("google").build();
//	}
//	private class CustomClientRegistrationRepository implements ClientRegistrationRepository {
//		@Override
//		public ClientRegistration findByRegistrationId(String registrationId) {
//			if ("google".equals(registrationId)) {
//				return googleClientRegistration();
//			}
//			return null;
//		}
//
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
//		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//		successHandler.setTargetUrlParameter("targetUrl");
//		return successHandler;
//	}
//}
