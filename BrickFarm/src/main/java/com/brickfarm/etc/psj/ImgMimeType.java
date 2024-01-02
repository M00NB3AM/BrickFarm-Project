package com.brickfarm.etc.psj;

import java.util.HashMap;
import java.util.Map;

public class ImgMimeType {
	private static Map<String, String> imgMimeType;

	{
		// instance 멤버변수 초기화 블럭
	}

	static {
		// static 변수의 초기화 블럭, this. 을 못쓰는 이유 : static변수라 클래스가 만들어지기 전에 변수 생성
		imgMimeType = new HashMap<String, String>();

		imgMimeType.put("jpg", "image/jpeg");
		imgMimeType.put("jpeg", "image/jpeg");
		imgMimeType.put("gif", "image/gif");
		imgMimeType.put("png", "image/png");
		imgMimeType.put("jfif", "image/jpeg");
	}

	public static boolean extIsImage(String ext) {
		return imgMimeType.containsKey(ext.toLowerCase());

	}
	public static boolean contentTypeIsIamge(String contentType) {
		return imgMimeType.containsValue(contentType);
	}
	

}

