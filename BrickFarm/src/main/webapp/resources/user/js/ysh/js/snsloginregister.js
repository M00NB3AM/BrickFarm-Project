var regId = /^[가-힣A-Za-z0-9]{4,8}$/;
var regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,10}$/;
var regName = /^[가-힣a-zA-Z]{2,15}$/;
var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
var pattern = /\s/g; //공백확인


function snsloginaddphone() {
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
      if (rsp.success) { //인증 성공시.
        $.ajax({
        url : '/portone/impinfo/' + rsp.imp_uid,
        type : 'post',
        data:{'imp_uid' : rsp.imp_uid}
        }).done(function(data){
        alert("인증 완료");
        console.log(data)
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


function inputdata(data){
  let phone1 = data.response.phone.substring(0, 3);
  let phone2 = data.response.phone.substring(3, 7);
  let phone3 = data.response.phone.substring(7);
  phone_number = phone1 + "-" + phone2 + "-" + phone3;

	if($('#phone_number').value != ""){
	$('#phone_number').val(phone_number);
  	$('#phone_number').prop("type", "text");
  	$('#checkPhoneNumber').prop("type", "hidden");
  	}
  	if($('#member_name').value != ""){
  	$('#member_name').val(data.response.name);
  	$('#member_name').prop("type", "text");
  	$('#checkPhoneNumber').prop("type", "hidden");
  	}
  let date = new Date(data.response.birth);
  let tmpyear = date.getFullYear().toString();
  let tmpmonth = (date.getMonth() + 1).toString();
  let tmpdate = date.getDate().toString();
  let month = "";
  let day ="";
  if (tmpmonth.length == 1) {
    month = "0" + tmpmonth;
  }else if(tmpmonth.length == 2){
  	month = tmpmonth;
  }
  if(tmpdate.length == 1){
  	day = "0" + tmpdate;
  }else if(tmpdate.length == 2){
  	day = tmpdate;
  }
  let year = tmpyear.substring(2);
  console.log(date);
  console.log(year);
  console.log(month);
  console.log(tmpdate);
  let birth = year + month + day;
  12/19
  if($('#birth_date').value != ""){
  		$('#birth_date').val(birth)
  	$('#checkPhoneNumber').prop("type", "hidden");
  	}
}



 function SnsgoPopup() {
        //주소찾기 버튼을 누르면 절대경로로 팝업창을 오픈
        var pop = window.open("/jusoPopup", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");
      }
      // prettier-ignore
      function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr,jibunAddr,zipNo,admCd,rnMgtSn,bdMgtSn,detBdNmList,bdNm,
            bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo) {
           	document.snsloginregister.address.value = roadFullAddr;
           //document.form.recipient_addr.value = roadAddrPart1;
           //document.form.recipient_detailed_addr.value = roadAddrPart2;
           //document.completeForm.recipient_detailed_addr.value = addrDetail;
           document.snsloginregister.zip_code.value = zipNo;
      }
      
      
//sns로그인 회원 추가정보 insert 하면서 ajax로 중복검사
function addsnsInfo() {
  let member_id = document.getElementById("member_id");
  let member_name = document.getElementById("member_name");
  let phone_number = document.getElementById("phone_number");
  let zip_code = document.getElementById("zip_code");
  let address = document.getElementById("address");
  
  
  if (member_id.value == "") {
    swal("", "아이디를 입력해 주세요.", "info");
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.value)) {
    swal("", "아이디는 4 ~ 8자 영문 대소문자, 한글, 숫자만 입력하세요.", "error");
    member_id.focus();
    return false;
  }else if (!isDuplicate) {
    $(".error").addClass("alert alert-danger").html("중복검사를 부탁드립니다.");
    member_id.focus();
    return false;
  }
  if (member_name.value == "" || phone_number.value == "") {
    swal("", "본인인증을 해주시기 바랍니다.", "info");
    member_name.focus();
    return false;
  }
  if (zip_code.value == "" || address.value == "") {
    swal("", "주소입력을 해주시기 바랍니다.", "info");
    zip_code.focus();
    return false;
  }
  let selectgender = document.getElementById("gender");
  let gender = selectgender.value;
  console.log(gender);
  
  if(gender == "" || gender =="Select"){
 	 swal("", "성별을 입력해주세요.", "info");
  	 gender.focus();
   	 return false;
  }
  //전화번호 중복 검사
  console.log("phoneNumber", phone_number.value);
   $.ajax({
    url: "/user/member/checkuserinfo",
    type: "post",
    data: { phone_number : phone_number.value },
    async: false,
    success: function (data) {
    console.log(data);
      if (data == phone_number.value) {
        swal("", "이미 등록되어있는 번호입니다.", "error");
        phone_number.focus();
        return false;
      }
      else {
 		 document.forms["snsloginregister"].submit();  
      }
    },
    error : function(error){
    console.log(error);
    }
    
  });
}



//SNS login member id 중복체크
function snsLoginCheckMemberid() {
  let member_id = document.getElementById('member_id');
  let zip_code = document.getElementById('zip_code');
  let address = document.getElementById('address');
  let phone_number = document.getElementById('phone_number');
  if (member_id.value == '') {
    swal('', '아이디를 입력해주세요.', 'error');
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.value)) {
    swal('', '아이디는 4 ~ 8자 영문 대소문자, 숫자만 입력하세요.', 'error');
    member_id.focus();
    return false;
  }
  $.ajax({
    url: '/user/member/snsmemberidcheck',
    type: 'post',
    data: { member_id: member_id.value},
    async: false,
    dataType: 'json',
    success: function (data) {
    console.log(data);
      if (data) {
        swal('', '중복된 닉네임 입니다.', 'error');
        isDuplicate = false;
        member_id.focus();
      } else {
        console.log('data', data);
        swal('', '사용가능한 닉네임 입니다.', 'success');
        isDuplicate = true;
      }
    },error : function(error){console.log(error, "중복확인 에러리어리ㅔ얼에ㅓㄹ메닝라")},
  });
}



