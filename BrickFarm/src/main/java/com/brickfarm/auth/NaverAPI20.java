package com.brickfarm.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

//싱글톤으로 생성(DefalutAPI에서 권장하는 싱글톤 방식은 일반 싱글톤 방식과 조금 다름.)
public class NaverAPI20 extends DefaultApi20 implements SnsUrls {
	private NaverAPI20() {
	}

	private static class InstanceHolder {
		private static final NaverAPI20 INSTANCE = new NaverAPI20();

	}

	public static NaverAPI20 instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {

		return NAVER_ACCESS_TOKEN;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return NAVER_AUTH;
	}
}
