//비밀번호 변경에서 사용
var regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,10}$/;

//새로운 비밀번호 변경하는 유효성 검사.
function checkmodifypwd() {
  let password = document.getElementById("Password");
  let password_confirm = document.getElementById("password_confirm");
  let member_id = document.getElementById("member_id");
  console.log(member_id);
  if (password.value === "") {
    alert("비밀번호를 입력해주세요.");
    password.focus();
    return false;
  } else if (password.value === member_id.value) {
    alert("아이디와 동일한 비밀번호는 불가능 합니다.");
    password.focus();
    return false;
  } else if (!regPwd.test(password.value)) {
    alert("비밀번호는 4자리~10자리만 가능합니다.");
    password.focus();
    return false;
  }
  if (password_confirm.value !== password.value) {
    alert("비밀번호가 동일하지 않습니다.");
    password_confirm.focus();
    return false;
  }
  
  //기존의 비밀번호와 같은지 비교하기 위한 ajax
   $.ajax({
    url: "/user/member/checkpassword",
    type: "post",
    data: { member_id: member_id.value},
    async: false,
    dataType: "json",
    success: function (data) {
      if (data == password.value) {
         swal("", "기존의 비밀번호와 같은 비밀번호는 사용할 수 없습니다.", "warning");
        password.focus();
        return false;
      } 
    },
  });
  document.forms["modifymemberpwd"].submit();
  
}
