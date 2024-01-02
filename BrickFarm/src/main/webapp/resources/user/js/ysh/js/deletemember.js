$(function(){
	 // <select> 요소 변경 시 실행될 함수
    	 $("#deletereason").hide();
    $("#selectreason").on("change", function() {
        // 선택된 옵션의 값 가져오기
        let selectedValue = $(this).val();
        // 선택된 옵션의 텍스트 가져오기
        let selectedText = $(this).find("option:selected").text();
        // <input> 요소에 선택된 값 표시
        $("#deletereason").val(selectedText);
        if (selectedValue === "10") {
            // "기타(직접입력)"이 선택되면 <input> 요소를 보여줌
            $("#deletereason").show();
            $("#deletereason").val("");
        } else {
            // 다른 옵션이 선택되면 <input> 요소를 숨김
            $("#deletereason").hide();
        }
    });
})




function deleteMember() {
  let password = document.getElementById("delete_password");
  let member_no = document.getElementById("member_no");
  let memo = document.getElementById('deletereason');
  if(memo.value == ""){
  swal("", "사유를 입력해주세요.", "warning");
  	return false;
  } 
 
  $.ajax({
    url: "/user/member/checkdeletememberpwd",
    type: "post",
    data: { password : password.value, member_no: member_no.value },
    async: false,
    success: function (data) {
    console.log(data);
      if (data == 'true') {
        console.log("data", data);
        document.forms["delete-form"].submit();
      }else if(data == 'false'){
        console.log("data", data);
        swal("", "비밀번호를 확인해주세요.", "warning");
        password.focus();
      }
    },
    error : function(error){swal("", "비밀번호를 확인해주세요.", "warning");},
  });
}


function snsdeleteMember() {
  let email = document.getElementById("email");
  let member_no = document.getElementById("member_no");
  let memo = document.getElementById('deletereason');
  if(memo.value == ""){
  swal("", "사유를 입력해주세요.", "warning");
  	return false;
  } 
 
  $.ajax({
    url: "/user/member/checkdeletesocialmemberemail",
    type: "post",
    data: { email : email.value, member_no: member_no.value },
    async: false,
    success: function (data) {
    console.log(data);
      if (data == 'true') {
        console.log("data", data);
        document.forms["delete-form"].submit();
      }else if(data == 'false'){
        console.log("data", data);
        swal("", "이메일을 확인해주세요.", "warning");
        password.focus();
      }
    },
    error : function(error){
    console.log(error);
    swal("", "이메일을 확인해주세요.", "warning");
    
    },
  });
}


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

function reset(){
	
 // 초기 모달 상태 저장
let initialModalState = $("#modalform").html();

// 리셋 함수 정의
function reset() {
  // 모달 내용 초기 상태로 복원
  $("#modalform").html(initialModalState);
  // 폼 리셋
  $("#modalform")[0].reset();
  $("#modalbody1").show();
  $("#beforediv").show();
  $("#afterdiv").hide();
  $("#modalspan1").show();
  $("#modalspan2").hide();
  $("#footmsg1").hide();
  $("#footmsg2").hide();
  
  
  
}

// 모달이 닫힐 때 초기 모달 상태 초기화
$('#myModal').on('hidden.bs.modal', function () {
  reset();
});
}


