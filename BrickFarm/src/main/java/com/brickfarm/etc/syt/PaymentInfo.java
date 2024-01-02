package com.brickfarm.etc.syt;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.brickfarm.dto.user.syt.CancelDataDTO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

public class PaymentInfo {
		private PaymentInfo(){}

		private static class SingletonHelper {
        	private static final PaymentInfo INSTANCE = new PaymentInfo();
    	}
	
		public static PaymentInfo getInstance() {
			return SingletonHelper.INSTANCE;
		}

		// 토큰 생성
		private IamportClient api = new IamportClient("7704034151804343","ZbBoKenp6HlaNRAlsgeTrWE6FwtB5RnMjNBUM0vxA84i7CccLK6jmhHCqYzj0HHv3ZFqLwU1pWKhgORR");
		// IMP 생성
		public String IMP = "imp57755803";
		
		// 결제 검증
		public IamportResponse<Payment> verifyPayment(int pay_money, String imp_uid) throws IamportResponseException, IOException {
			IamportResponse<Payment> verifyPayment = null;
			IamportResponse<Payment> responsePayment = paymentByImpUid(imp_uid);
	
			if(responsePayment.getResponse().getAmount().intValue() == pay_money) {
				verifyPayment = responsePayment;
			}
			return verifyPayment;
		}
		
		// 결제 조회
		public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {	
			return api.paymentByImpUid(imp_uid); 
		}
		
		// 취소
		public IamportResponse<Payment> cancelPaymentByImpUid(CancelDataDTO cancelData) throws IamportResponseException, IOException {			
			CancelData data = new CancelData(cancelData.getImp_uid(), true, cancelData.getCancel_request_amount());
			data.setChecksum(cancelData.getChecksum());
			data.setReason(cancelData.getReason());
			return api.cancelPaymentByImpUid(data);
		}
		
		// 인증 조회
		public IamportResponse<Certification> certificationByImpUid(String imp_uid) throws IamportResponseException, IOException {				
			return api.certificationByImpUid(imp_uid);
		}
		
		// merchant_uid 생성하는곳
		public String createMerchantUid(String member_id) {
			Calendar cal = Calendar.getInstance();
			String merchant_uid = member_id + "_" + cal.get(Calendar.YEAR)
					+ new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1) 
					+ new DecimalFormat("00").format(cal.get(Calendar.DATE)) 
					+ new DecimalFormat("00").format(cal.get(Calendar.HOUR_OF_DAY)) 
					+ new DecimalFormat("00").format(cal.get(Calendar.MINUTE)) 
					+ new DecimalFormat("00").format(cal.get(Calendar.SECOND));
			return merchant_uid;
		}
}
