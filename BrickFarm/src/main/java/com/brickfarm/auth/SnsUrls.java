package com.brickfarm.auth;

public interface SnsUrls {

	// 네이버
	static final String NAVER_ACCESS_TOKEN = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	static final String NAVER_AUTH = "https://nid.naver.com/oauth2.0/authorize";
	static final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";

	// 구글
	static final String GOOGLE_PROFILE_URL = "https://people.googleapis.com/v1/people/me?personFields=addresses,birthdays,emailAddresses,phoneNumbers,names,genders&sources=READ_SOURCE_TYPE_PROFILE&alt=json&key=AIzaSyARGfZnceempdhTOzg3xuLgE2KqLuAJtTw";

	// 카카오
	static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token";
	static final String KAKAO_AUTH = "https://kauth.kakao.com/.well-known/jwks.json";
	static final String KAKAO_PROFILE_URL = "https://kauth.kakao.com/oauth/authorize";
}

//?client_id=54268092a1fd5d051f68bdc44d4796cf&redirect_uri=http://localhost:8081/auth/kakao/callback&response_type=code&scope=account_email