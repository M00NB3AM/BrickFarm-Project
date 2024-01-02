function importVerify() {
  IMP.init("imp74035534");
  // IMP.certification(param, callback) 호출
  IMP.certification(
    {
      // param
      // 주문 번호
      pg: "MIIiasTest", //본인인증 설정이 2개이상 되어 있는 경우 필
      merchant_uid: "ORD20180131-0000011",
      // 모바일환경에서 popup:false(기본값) 인 경우 필수
      m_redirect_url: "http://localhost:8081/loginregistermodel",
      // PC환경에서는 popup 파라미터가 무시되고 항상 true 로 적용됨
      popup: false,
    },
    function (rsp) {
      console.log(rsp);
      //결제 검증..
      if (rsp.success) {
        //인증 성공시.
        $.ajax({
          url: "/portone/impinfo/" + rsp.imp_uid,
          type: "post",
          data: { imp_uid: rsp.imp_uid },
        }).done(function (data) {
          alert("인증 완료");
          console.log(data);
          console.log(data.response.birth);
          inputdata(data);
        });
      } else {
        alert("인증 실패 : " + rsp.error_msg);
        console.log(실패);
        // 인증 실패 시 로직.
      }
    }
  );
}

function inputdata(data) { // 전화번호 수정
  let phone1 = data.response.phone.substring(0, 3);
  let phone2 = data.response.phone.substring(3, 7);
  let phone3 = data.response.phone.substring(7);
  phone_number = phone1 + "-" + phone2 + "-" + phone3;

  console.log(data.response.phone);
  if ($("#phone_number").value != "") {
    $("#phone_number").val(phone_number);
    $("#phone_number").prop("type", "text");
  }
  if($("#member_name").value != ""){
    $("#member_name").val(data.response.name);
    $("#member_name").prop("type", "text");
  }
}


//회원 정보 수정
function mypagemodify(){
	console.log('mypagemodifyprofile.js : 회원의 정보를 수정합니다.');
	let member_no = document.getElementById("member_no");
	let member_name = document.getElementById("member_name");
	let phone_number = document.getElementById("phone_number");
	let email = document.getElementById("email");
	let zip_code = document.getElementById("zip_code");
	let address = document.getElementById("address");
	
	
	if(address.value == ""){
	 swal("", "주소를 입력해주세요.", "info");
	 address.focus();
	 return false;
	}

	$.ajax({
    url: "/user/member/mypageinfomodify",
    type: "post",
    data: { member_no: member_no.value,
     member_name : member_name.value,
     phone_number : phone_number.value,
     email : email.value,
     zip_code : zip_code.value,
     address : address.value},
    async: false,
    dataType : "json",
    success: function (data) {
    console.log(data)
      if (data.status == true) {
         swal("", "저장되었습니다.", "success");
      } else {
         swal("", "기존에 등록되어있는 정보가 있습니다.", "error");
      }
    },
    error : function(error){
    	console.log(error);
    }
  });
}