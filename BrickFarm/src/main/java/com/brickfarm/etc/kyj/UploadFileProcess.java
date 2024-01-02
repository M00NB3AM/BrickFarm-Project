package com.brickfarm.etc.kyj;

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

/**
 * @packageName : com.brickfarm.etc.kyj
 * @fileName : UploadFileProcess.java
 * @author : kyj
 * @date : 2023. 10. 10.
 * @description : 문의 게시판에 업로드 된 파일을 처리한다.
 */
public class UploadFileProcess {

	/**
	 * @methodName : fileUpload
	 * @author : kyj
	 * @param originalFileName
	 * @param size
	 * @param contentType
	 * @param data
	 * @param realPath
	 * @return
	 * @throws IOException
	 * @returnValue : @param originalFileName
	 * @returnValue : @param size
	 * @returnValue : @param contentType
	 * @returnValue : @param data
	 * @returnValue : @param realPath
	 * @returnValue : @return
	 * @returnValue : @throws IOException
	 * @date : 2023. 10. 10. 오후 5:14:05
	 * @description : 파일 업로드 처리의 전체 컨트롤
	 */
	public static UserInquiryImagesVO fileUpload(String originalFileName, long size, String contentType, byte[] data,
			String realPath) throws IOException {

		makeCalculatedPath(realPath); // 물리적경로 + /년/월/일

		UserInquiryImagesVO uploadFile = new UserInquiryImagesVO();

		if (size > 0) {
			uploadFile.setNew_file_name(getDatePath() + getUniqueFileName(originalFileName));
			uploadFile.setOriginal_file_name(originalFileName);
			uploadFile.setFile_size(size);

			// System.out.println(uploadFile.getNew_file_name());
			// System.out.println(realPath);

			if (ImgMimeType.isImageContentType(contentType)) {
				// 파일의 종류가 이미지
				// 스케일 다운 -> thumbnail 이름으로 파일 저장
				try {
					FileCopyUtils.copy(data, new File(realPath + uploadFile.getNew_file_name()));

					try {
						makeThumbNailImg(uploadFile, realPath);
					} catch (IOException e) {
						// 섬네일 파일 저장이 실패하였을 경우 미리 업로드된 원본파일에 대한 삭제 처리
						e.printStackTrace();
						// System.out.println("썸네일 파일 저장 실패");
						File originalFile = new File(realPath + uploadFile.getNew_file_name());
						if (originalFile.exists()) {
							originalFile.delete();
						} else {
							uploadFile = null;
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
					// System.out.println("원본 파일 저장 실패");
					uploadFile = null;
				}

			} else {
				// System.out.println("업로드된 파일이 이미지가 아닙니다.");
				uploadFile = null;
			}
		} else {
			// size가 0이다. 업로드된 파일에 문제가 있다.
			uploadFile = null;
		}

//		if (uploadFile != null) {
//			System.out.println(uploadFile.toString());
//		}

		return uploadFile;
	}

	/**
	 * @methodName : makeThumbNailImg
	 * @author : kyj
	 * @param uploadFile
	 * @param realPath
	 * @throws IOException
	 * @returnValue : @param uploadFile
	 * @returnValue : @param realPath
	 * @returnValue : @throws IOException
	 * @date : 2023. 10. 10. 오후 5:58:12
	 * @description : 이미지(원본)를 읽어와 스케일 다운시키고, 썸네일 파일로 저장
	 */
	private static void makeThumbNailImg(UserInquiryImagesVO uploadFile, String realPath) throws IOException {
		BufferedImage originImg = ImageIO.read(new File(realPath + uploadFile.getNew_file_name())); // 원본파일

		BufferedImage thumbNailImg = Scalr.resize(originImg, Mode.FIT_TO_HEIGHT, 70);

		String thumbImgName = getThumbNailFileName(uploadFile);

		File saveTarget = new File(realPath + getDatePath() + thumbImgName);

		String ext = uploadFile.getNew_file_name().substring(uploadFile.getNew_file_name().lastIndexOf(".") + 1);

		if (ImageIO.write(thumbNailImg, ext, saveTarget)) { // 썸네일 이미지를 저장 -> 성공이면 uf에 담기
			uploadFile.setThumbnail_file_name(getDatePath() + thumbImgName);
		}
	}

	/**
	 * @methodName : makeCalculatedPath
	 * @author : kyj
	 * @param realPath
	 * @return
	 * @returnValue : @param realPath
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 5:57:53
	 * @description : uploads/년/월/일 구조로 폴더를 만들되 해당 이름으로 폴더가 존재하지 않으면 만들고 최종경로를
	 *              반환해준다.
	 */
	public static String makeCalculatedPath(String realPath) {
		Calendar cal = Calendar.getInstance();

		String year = File.separator + (cal.get(Calendar.YEAR) + "");
		String month = year + File.separator + new DecimalFormat("00").format((cal.get(Calendar.MONTH) + 1));
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		// System.out.println(year);
		// System.out.println(month);
		// System.out.println(date);

		makeDirectory(realPath, year, month, date);

		return realPath + date;
	}

	/**
	 * @methodName : makeDirectory
	 * @author : kyj
	 * @param realPath
	 * @param strings
	 * @returnValue : @param realPath
	 * @returnValue : @param strings
	 * @date : 2023. 10. 10. 오후 5:57:37
	 * @description : realPath 아래에 현재 날짜의 /년/월/일 구조로 되어있는 디렉토리가 없으면 해당 디렉토리를 생성한다.
	 */
	// ... strings : 가변인자 메서드 기법 (전달된 year, month, date 값이 strings 하나의 매개변수로
	// 할당된다.(배열 형식으로)
	private static void makeDirectory(String realPath, String... strings) { // 구조문의 할당 가변 인자 문법
		if (!new File(realPath + strings[strings.length - 1]).exists()) {
			for (String path : strings) {
				File tmp = new File(realPath + path);
				if (!tmp.exists()) {
					tmp.mkdir();
				}
			}
		}
	}

	/**
	 * @methodName : getUniqueFileName
	 * @author : kyj
	 * @param originalFileName
	 * @return
	 * @returnValue : @param originalFileName
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 5:57:23
	 * @description : /원/월/일/ 경로, UUID, 원본파일명, 확장자를 이어붙인 새 파일명을 반환한다.
	 */
	private static String getUniqueFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "_" + originalFileName;
	}

	/**
	 * @methodName : getDatePath
	 * @author : kyj
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 5:57:15
	 * @description : "/YYYY/MM/DD/" 포맷의 오늘 날짜를 기반으로한 경로 문자열을 반환한다.
	 */
	private static String getDatePath() {
		Calendar cal = Calendar.getInstance();

		String year = File.separator + cal.get(Calendar.YEAR);
		String month = year + File.separator + new DecimalFormat("00").format((cal.get(Calendar.MONTH) + 1));
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		return date + File.separator;
	}

	/**
	 * @methodName : getThumbNailFileName
	 * @author : kyj
	 * @param uploadFile
	 * @return
	 * @returnValue : @param uploadFile
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 5:56:22
	 * @description : uploadFile의 new_file_name으로부터 앞부분의 \년\월\일\ 부분을 뗀 파일명의 앞부분에
	 *              "thumb_" 문자열을 붙여 반환한다.
	 */
	private static String getThumbNailFileName(UserInquiryImagesVO uploadFile) {
		String newFileName = uploadFile.getNew_file_name(); // \YYYY\MM\DD\filename
		String pureFileName = newFileName.substring(newFileName.lastIndexOf(File.separator) + 1);
		String thumbImgName = "thumb_" + pureFileName;
		// System.out.println("debug : 섬네일파일명");
		// System.out.println(newFileName);
		// System.out.println(pureFileName);
		// System.out.println(thumbImgName);
		return thumbImgName;
	}

	/**
	 * @methodName : deleteFile
	 * @author : kyj
	 * @param fileList
	 * @param removeFile
	 * @param realPath
	 * @returnValue : @param fileList
	 * @returnValue : @param removeFile
	 * @returnValue : @param realPath
	 * @date : 2023. 10. 10. 오후 5:56:16
	 * @description : fileList의 항목중 new_file_name이 removeFile과 같은 항목의 파일을 삭제한다.
	 */
	public static void deleteFile(List<UserInquiryImagesVO> fileList, String removeFile, String realPath) {
		for (UserInquiryImagesVO uploadFile : fileList) {
			if (removeFile.equals(uploadFile.getNew_file_name())) {
				File deleteFile = new File(realPath + uploadFile.getNew_file_name());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				if (uploadFile.getThumbnail_file_name() != null) {
					File thumbFile = new File(realPath + uploadFile.getThumbnail_file_name());
					if (thumbFile.exists()) {
						thumbFile.delete();
					}
				}
			}
		}
	}

	/**
	 * @methodName : deleteAllFile
	 * @author : kyj
	 * @param fileList
	 * @param realPath
	 * @returnValue : @param fileList
	 * @returnValue : @param realPath
	 * @date : 2023. 10. 10. 오후 5:56:08
	 * @description : fileList에 있는 모든 항목에 해당하는 파일들을 삭제한다.
	 */
	public static void deleteAllFile(List<UserInquiryImagesVO> fileList, String realPath) {
		for (UserInquiryImagesVO uploadFile : fileList) {
			File deleteFile = new File(realPath + uploadFile.getNew_file_name());
			if (deleteFile.exists()) {
				deleteFile.delete();
			}
			if (uploadFile.getThumbnail_file_name() != null) {
				File thumbFile = new File(realPath + uploadFile.getThumbnail_file_name());
				if (thumbFile.exists()) {
					thumbFile.delete();
				}
			}
		}
	}


}
