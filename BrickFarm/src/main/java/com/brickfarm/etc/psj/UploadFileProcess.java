package com.brickfarm.etc.psj;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.util.FileCopyUtils;

import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;
import com.brickfarm.vo.user.psj.UserProductReviewImageVO;

public class UploadFileProcess {
	public static UserProductReviewImageVO fileUpload(String originalFilename, long size, String contentType,
			byte[] data, String realPath) throws IOException {
		// 파일의 물리적 경로
		makeCalculatedPath(realPath);
		
		UserProductReviewImageVO uploadFile = new UserProductReviewImageVO();
		
		if(size > 0) {
			uploadFile.setNew_file_name(getDatePath() + getUniqueFileName(originalFilename));
			uploadFile.setOriginal_file_name(originalFilename);
			uploadFile.setFile_size(size);
			
			System.out.println("파일의 originalName : " + originalFilename);
			System.out.println("파일의 newFileName : " + uploadFile.getNew_file_name());
			System.out.println("파일의 설치 경로 : " + realPath);
			
			if(ImgMimeType.contentTypeIsIamge(contentType)) {
				System.out.println(realPath + uploadFile.getNew_file_name());
				FileCopyUtils.copy(data, new File(realPath, uploadFile.getNew_file_name()));
				try {
					makeThumbnailImg(uploadFile, realPath);
				} catch (Exception e) {
					System.out.println("썸네일 이미지 저장 에러");
					File originalFile = new File(realPath + uploadFile.getNew_file_name());
					if(originalFile.exists()) {
						originalFile.delete();
					} else {
						uploadFile = null;
					}
					e.printStackTrace();
				}
			}
		}
		
		return uploadFile;
	}
	
	public static void deleteFile(List<UserProductReviewImageVO> fileList, String removeFile, String realPath) {
		for(UserProductReviewImageVO uploadFile : fileList) {
			if(removeFile.equals(uploadFile.getNew_file_name())) {
				File deleteFile = new File(realPath + uploadFile.getNew_file_name());
				if(deleteFile.exists()) {
					deleteFile.delete();
				}
				if(uploadFile.getThumb_file_name() != null) {
					File thumbFile = new File(realPath + uploadFile.getThumb_file_name());
					if(thumbFile.exists()) {
						thumbFile.delete();
					}
				}
			}
		}
	}
	
	
	private static void makeThumbnailImg(UserProductReviewImageVO uploadFile, String realPath) throws Exception {
		BufferedImage originalImg = ImageIO.read(new File(realPath + uploadFile.getNew_file_name())); 
		
		BufferedImage thumbnailImg = Scalr.resize(originalImg, Mode.FIT_TO_HEIGHT, 70);
		
		String thumbnailImgName = getThumbnailFileName(uploadFile);
		
		File saveFile = new File(realPath + getDatePath() + thumbnailImgName);
		
		String ext = uploadFile.getNew_file_name().substring(uploadFile.getNew_file_name().lastIndexOf(".") + 1);
		
		if(ImageIO.write(thumbnailImg, ext, saveFile)) {
			uploadFile.setThumb_file_name(getDatePath() + thumbnailImgName);
		}
		
	}
	
	
	// 년/월/일 구조의 폴더 생성
	public static String makeCalculatedPath(String realPath) {
		Calendar cal = Calendar.getInstance();
		
		String year = File.separator + (cal.get(Calendar.YEAR) + "");
		String month = year + File.separator + new DecimalFormat("00").format((cal.get(Calendar.MONTH) + 1)); 
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDirectory(realPath, year, month, date);
		System.out.println(realPath+date);
		return realPath + date;
	}
	
	private static void makeDirectory(String realPath, String... strings) {
		if(!new File(realPath + strings[strings.length - 1]).exists()) {
			for(String path : strings) {
				File tmp = new File(realPath + path);
				if(!tmp.exists()) {
					tmp.mkdir();
					System.out.println(tmp.getPath());
					System.out.println("폴더 생성");
				}
			}
		}
	}
	
	private static String getDatePath() {
		Calendar cal = Calendar.getInstance();
		
		String year = File.separator + cal.get(Calendar.YEAR);
		String month = year + File.separator + new DecimalFormat("00").format((cal.get(Calendar.MONTH) + 1));
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		return date + File.separator;
	}
	
	private static String getUniqueFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "_" + originalFileName;
	}
	
	private static String getThumbnailFileName(UserProductReviewImageVO uploadFile) {
		String newFileName = uploadFile.getNew_file_name();
		String FileName = newFileName.substring(newFileName.lastIndexOf(File.separator) + 1);
		String thumbnailImgName = "thumb_" + FileName;
		return thumbnailImgName;
	}
	
}
