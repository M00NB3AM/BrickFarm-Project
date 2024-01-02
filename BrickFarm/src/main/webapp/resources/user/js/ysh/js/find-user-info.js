//아이디 비밀번호 찾기에서 사용중

var regId = /^[가-힣A-Za-z0-9]{4,8}$/;
var regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,10}$/;
var regName = /^[가-힣a-zA-Z]{2,15}$/;
var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;

$(function(){

	 $("#id_phone_number").hide();
	 $("#id_member_name").hide();
	 $("#pwd_phone_number").hide();
	 $("#pwd_member_name").hide();
})

function find_userid() {
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
          swal("", "인증되었습니다.", "success");
          inputiddata(data);
        });
      } else {
        swal("", "인증에 실패했습니다. 다시 부탁드립니다.", "warning");
        // 인증 실패 시 로직.
      }
    }
  );
}

function inputiddata(data) {
  console.log(data.response.name);
  console.log(data.response.phone);
  if ($("#id_phone_number").value != "") {
    $("#id_phone_number").val(data.response.phone);
    $("#id_phone_number").prop("type", "text");
    $("#id_phone_number").show();
    $("#idbutton").hide();
  }
  if($("#id_member_name").value != ""){
  	 $("#id_member_name").val(data.response.name);
    $("#id_member_name").prop("type", "text");
    $("#id_member_name").show();
    $("#idbutton").hide();
  }
}

function find_userpwd() {
  IMP.init("imp74035534");
  IMP.certification(
    {
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
          swal("", "인증되었습니다.", "success");
          
          inputpwddata(data);
        });
      } else {
        swal("", "인증에 실패했습니다. 다시 부탁드립니다.", "warning");
        console.log(실패);
        // 인증 실패 시 로직.
      }
    }
  );
}
function inputpwddata(data) {
  console.log(data.phone);
  if ($("#pwd_phone_number").value != "") {
    $("#pwd_phone_number").val(data.response.phone);
    $("#pwd_phone_number").prop("type", "text");
    $("#pwd_phone_number").show();
    $("#pwdbutton").hide();
  }
  if ($("#pwd_member_name").value != "") {
    $("#pwd_member_name").val(data.response.name);
    $("#pwd_member_name").prop("type", "text");
    $("#pwd_member_name").show();
    $("#pwdbutton").hide();
  }
}
//아이디 찾기 유효성 검사
function availabilityuserid() {
  let member_name = document.getElementById("id_member_name");
  let phone_number = document.getElementById("id_phone_number");

  if (member_name.value == "") {
    swal("", "본인인증을 진행해 주세요.", "warning");
    member_name.focus();
    return false;
  } else if (!regName.test(member_name.value)) {
    swal("", "본인인증을 진행해주세요.", "warning");
    member_name.focus();
    return false;
  } else if (phone_number.value == "") {
    swal("", "본인인증은 진행해주세요.", "warning");
    phone_number.focus();
    return false;
  }
  document.forms["find_user_id"].submit();
}

//비밀번호 찾기 유효성 검사
function availabilityuserpwd() {
  let member_name = document.getElementById("pwd_member_name");
  let member_id = document.getElementById("pwd_member_id");
  let phone_number = document.getElementById("pwd_phone_number");

  if (member_id.value == "") {
    registershakeModal();
    swal("", "아이디를 입력하세요.", "warning");
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.value)) {
    registershakeModal();
    swal("", "아이디는 4 ~ 8자 영문 대소문자, 숫자, 한글만 입력가능합니다.", "warning");
    member_id.focus();
    return false;
  }
  
  if (member_name.value == "") {
    swal("", "본인인증을 부탁드립니다.", "warning");
    member_name.focus();
    return false;
  } else if (!regName.test(member_name.value)) {
   swal("", "본인인증을 진행해주세요.", "warning");
    member_name.focus();
    return false;
  }
  if (phone_number.value == "") {
    swal("", "본인인증을 진행해주세요.", "warning");
    phone_number.focus();
    return false;
  }
  document.forms["find_user_password"].submit();
}


const time_to_show_login = 400;
const time_to_hidden_login = 200;

function change_to_login() {
document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_login";  
document.querySelector('.cont_form_login').style.display = "block";
document.querySelector('.cont_form_sign_up').style.opacity = "0";               

setTimeout(function(){  document.querySelector('.cont_form_login').style.opacity = "1"; },time_to_show_login);  
  
setTimeout(function(){    
document.querySelector('.cont_form_sign_up').style.display = "none";
},time_to_hidden_login);  
  }

  const time_to_show_sign_up = 100;
  const time_to_hidden_sign_up = 400;

function change_to_sign_up(at) {
  document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_sign_up";
  document.querySelector('.cont_form_sign_up').style.display = "block";
document.querySelector('.cont_form_login').style.opacity = "0";
  
setTimeout(function(){  document.querySelector('.cont_form_sign_up').style.opacity = "1";
},time_to_show_sign_up);  

setTimeout(function(){   document.querySelector('.cont_form_login').style.display = "none";
},time_to_hidden_sign_up);  


}    

const time_to_hidden_all = 500;

function hidden_login_and_sign_up() {

document.querySelector('.cont_forms').className = "cont_forms";  
document.querySelector('.cont_form_sign_up').style.opacity = "0";               
document.querySelector('.cont_form_login').style.opacity = "0"; 

setTimeout(function(){
document.querySelector('.cont_form_sign_up').style.display = "none";
document.querySelector('.cont_form_login').style.display = "none";
},time_to_hidden_all);  
  
  }
 