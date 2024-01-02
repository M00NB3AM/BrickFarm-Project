package com.brickfarm.controller.user.member.ysh.apicontroller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;

@Controller
public class IamPortPhoneNumber {
	private IamportClient api;

	public IamPortPhoneNumber() {
		this.api = new IamportClient("3380285527352817",
				"S1Jhzwlc8oEjG7Pojn7Y4zXo6ukEhJUAJiNXo8Ed1lEW4fWt95yHTYxJ07PRZLXvZc24slX6Q6PBzIa6");
	}

	@ResponseBody
	@RequestMapping(value = "/portone/impinfo/{imp_uid}", method = RequestMethod.POST)
	public IamportResponse<Certification> certificationByImpUid(@PathVariable(value = "imp_uid") String imp_uid)
			throws IamportResponseException, IOException {

		System.out.println(imp_uid);
		return api.certificationByImpUid(imp_uid);

	}

}
