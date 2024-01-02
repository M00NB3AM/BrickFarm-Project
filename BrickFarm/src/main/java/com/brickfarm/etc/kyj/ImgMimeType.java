package com.brickfarm.etc.kyj;

import java.util.HashMap;
import java.util.Map;

public class ImgMimeType {
	private static Map<String, String> imgMimeType;
	
	{
		// instance 멤버변수 초기화 블럭
	}
	
	static {
		// static 초기화 블럭
		imgMimeType = new HashMap<String, String>();
		imgMimeType.put("jpg", "image/jpeg");
		imgMimeType.put("jpeg", "image/jpeg");
		imgMimeType.put("gif", "image/gif");
		imgMimeType.put("png", "image/png");
	}
	
	public static Boolean isImageExt(String ext) {
		return imgMimeType.containsKey(ext.toLowerCase());
	}
	
	public static Boolean isImageContentType(String contentType) {
		return imgMimeType.containsValue(contentType);
	}
}