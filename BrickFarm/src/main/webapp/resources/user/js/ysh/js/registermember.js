//일반 회원가입에서 사용
$(function () {
  $("#modalbody1").show();
  $("#beforediv").show();
  $("#afterdiv").hide();
  $("#modalspan1").show();
  $("#modalspan2").hide();
  $("#footmsg1").hide();
  $("#footmsg2").hide();
  let initialModalState = document.getElementById('modalform').innerHTML;
});


let number = "";
//이메일 인증번호 받는 함수
function emailconfirm() {
  let email = document.getElementById("modalemail");
  if (email.value == "") {
    swal("", "이메일을 입력해주세요.", "error");
    return false;
  } else if (email.value != "") {
    $.ajax({
      url: "/find",
      type: "post",
      data: { email: email.value },
      async: false,
      dataType: "json",
      success: function (data) {
        console.log(data); // 인증번호 넘어옴.
        alert("메일 발송");
        number = data;
        if (data != null) {
          $("#beforemodalemail").val("");
          $("#beforediv").hide();
          $("#afterdiv").show();
          $("#modalspan1").hide();
          $("#modalspan2").show();
          $("#footmsg1").hide();
          $("#footmsg2").hide();
        } else {
          alert("인증번호 발송에 실패했습니다.");
        }
      },
      error: function (error) {
        console.log(error);
      },
    });
  }
}

function confirm() {
  let emailnum = document.getElementById("emailnum");
  let modalemail = document.getElementById("modalemail");
  console.log(number);
  if (number == emailnum.value) {
    // 인증번호가 같다면
    $("#footmsg2").show();
  } else if (number != emailnum.value) {
    // 같지 않다면
    modalemail.val("");
    $("#footmsg1").show();
    $("#footmsg2").hide();
  }
}

function complite() {
  let modalemail = document.getElementById("modalemail");
  email.value = modalemail.value;
}
//-------------------------------------------------- 이메일 ------------------------------------------------
//-------------------------------------------------- 유효성검사 ---------------------------------------------
let isDuplicate = false;
var regId = /^[가-힣A-Za-z0-9]{4,8}$/;
var regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,10}$/;
var regName = /^[가-힣a-zA-Z]{2,15}$/;
var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
var pattern = /\s/g; //공백확인

$(function () {
  $("#member_id").change(function () {
    if (isDuplicate) {
      isDuplicate = false;
    }
  });
});

//아이디 중복검사..
function checkUserId() {
  let member_id = $("#member_id");
  if (member_id.val() == "") {
    swal("", "아이디를 입력해주세요.", "warning");
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.val())) {
    swal("", "아이디는 4 ~ 8자 영문 대소문자, 숫자, 한글만 입력가능합니다.", "warning");
    member_id.focus();
    return false;
  } else if (pattern.test(member_id.val())) {
    swal("", "공백은 사용하실수 없습니다.", "warning");
    member_id.focus();
    return false;
  }
  $.ajax({
    url: "/user/member/checkUserid",
    type: "post",
    data: { member_id: member_id.val() },
    async: false,
    success: function (data) {
      console.log(data);
      if (data == "duplicate") {
        swal("", "중복된 아이디입니다.", "error");
        isDuplicate = false;
        member_id.focus();
      } else {
        swal("", "사용가능한 아이디입니다.", "success");
        isDuplicate = true;
      }
    },
  });
}

//일반유저 회원가입 부분
function registerMember() {
  console.log("회원가입유효성검사.");
  //유효성검사
  var member_id = document.getElementById("member_id");
  var userId_confirm = document.getElementById("userId_confirm");
  var password = document.getElementById("password");
  var password_confirm = document.getElementById("password_confirm");
  var member_name = document.getElementById("member_name");
  var phone_number = document.getElementById("phone_number");
  var selectgender = document.getElementById("gender");
  var email = document.getElementById("email");
  var birth_date = document.getElementById("birth_date");
  var zip_code = document.getElementById("zip_code");
  var address = document.getElementById("address");
  let gender = selectgender.value;
  console.log(gender);
 
  //id 확인.
  if (member_id.value == "") {
    swal("", "아이디를 입력하세요.", "warning");
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.value)) {
    swal("", "아이디는 4 ~ 8자 영문 대소문자, 숫자, 한글만 입력가능합니다.", "warning");
    member_id.focus();
    return false;
  } else if (!isDuplicate) {
    swal("", "중복검사를 부탁드립니다.", "warning");
    member_id.focus();
    return false;
  }
  //비밀번호 확인.
  if (password.value == "") {
    swal("", "비밀번호를 입력하세요.", "warning");
    password.focus();
    return false;
    //아이디와 비밀번호가 같은지.
  } else if (password.value == member_id.value) {
    swal("", "아이디와 동일한 비밀번호를 사용할 수 없습니다.", "warning");
    password.focus();
    return false;
    //비밀번호 자리수 검사
  } else if (!regPwd.test(password.value)) {
    swal("", "비밀번호는 문자, 숫자, 특수문자를 합한 8자리~10자리만 가능합니다.", "warning");
    password.focus();
    return false;
  }
  //비밀번호 더블체크
  if (password_confirm.value !== password.value) {
    swal("", "비밀번호가 동일하지 않습니다.", "warning");
    password_confirm.focus();
    return false;
  }
  
   if (phone_number.value == "" && member_name.value == "") {
    swal("", "본인인증을 부탁드립니다.", "warning");
    return false;
  }
  //성별 확인.
  if (gender.value == "") {
    swal("", "본인인증을 해주세요.", "warning");
    gender.focus();
    return false;
  }
  if (address.value == "") {
    swal("", "주소입력을 부탁드립니다.", "info");
    address.focus();
    return false;
  }
  //이메일주소 확인.
  if (email.value.length == 0) {
    swal("", "이메일을 입력해주세요.", "warning");
    email.focus();
    return false;
  } else if (!regEmail.test(email.value)) {
    swal("", "잘못된 이메일형식입니다.", "warning");
    email.focus();
    return false;
  }

  //이름 확인. 한글과 영어만 확인.
  if (member_name.value == "") {
    swal("", "본인인증을 부탁드립니다.", "warning");
    member_name.focus();
    return false;
  } else if (!regName.test(member_name.value)) {
    swal("", "본인인증을 부탁드립니다.", "warning");
    member_name.focus();
    return false;
  }
	
   $.ajax({
    url: "/user/member/checkedmemberforregister",
    type : "post",
    data : { member_name : member_name.value, phone_number : phone_number.value, email : email.value },
    async : false,
    dataType : "json",
    success : function (data) {
      console.log(data);
      if (data.email == email.value) {
      console.log(data.email);
        // 같은 이메일이 있다면
        swal("", "이미 가입되어있는 이메일 입니다..", "error");
        email.focus();
        return false;
      }if (data.phone_number == phone_number.value) {
      console.log(data.phone_number);
        swal("", "이미 가입된 전화번호입니다.", "error");
        phone_number.focus();
      	return false;
      }if (data.social_check != null) {
      console.log(data.social_check);
        swal("", "이미 가입된 소셜 유저입니다.", "error");
        email.focus();
        return false;
      }else {
		//유효성에 문제가 없다면 submit
  		document.forms["registerform"].submit();
      }
    },
    error: function (error) {
      swal("", "회원가입에 실패했습니다.잠시후 다시 시도해주세요.", "warning");
      console.log(error);
      return false;
    },
  });
}
//회원가입에서 사용중
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

function inputdata(data) {
  console.log(data.response.name);
  console.log(data.response.phone);

  let phone1 = data.response.phone.substring(0, 3);
  let phone2 = data.response.phone.substring(3, 7);
  let phone3 = data.response.phone.substring(7);
  phone_number = phone1 + "-" + phone2 + "-" + phone3;
  console.log(phone_number);
  if ($("#phone_number").value != "") {
    $("#phone_number").val(phone_number);
    $("#phone_number").prop("type", "text");
    $("#checkPhoneNumber").prop("type", "hidden");
  }
  if ($("#member_name").value != "") {
    $("#member_name").val(data.response.name);
    $("#member_name").prop("type", "text");
    $("#checkPhoneNumber").prop("type", "hidden");
  }
  if ($("#birth_date").value != "") {
    $("#birth_date").val(data.response.name);
    $("#checkPhoneNumber").prop("type", "hidden");
  }

  let date = new Date(data.response.birth);
  let tmpyear = date.getFullYear().toString();
  let tmpmonth = (date.getMonth() + 1).toString();
  let tmpdate = date.getDate().toString();
  let month = "";
  let day = "";
  if (tmpmonth.length == 1) {
    month = "0" + tmpmonth;
  }
  if (tmpdate.length == 1) {
    day = "0" + tmpdate;
  }
  let year = tmpyear.substring(2);
  console.log(date);
  console.log(year);
  console.log(month);
  console.log(tmpdate);
  let birth = year + month + day;

  if ($("#birth_date").value == "") {
    $("#checkPhoneNumber").show();
  } else if ($("#birth_date").value != "") {
    $("#birth_date").val(birth);
    $("#birth_date").prop("type", "text");
    $("#birth-div").show();
    $("#checkPhoneNumber").hide();
  }
}


function reset(){
	document.getElementById('modalform').reset();
	document.getElementById('modalform').innerHTML = initialModalState;
	document.getElementById('myModal').style.display = 'none';
}

