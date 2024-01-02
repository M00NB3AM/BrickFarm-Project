window.onload = function () {
  document.getElementById('login_password').addEventListener('keyup', function (event) {
    if (event.keyCode == 13) {
      loginMember();
    }
  });
};

//유저 로그인
function loginMember() {
  let member_id = document.getElementById('login_userId');
  let password = document.getElementById('login_password');

  if (member_id.value == '') {
    swal('', '아이디를 입력해주세요.', 'warning');
    member_id.focus();
    return false;
  }
  if (password.value == '') {
    swal('', '비밀번호를 입력해주세요.', 'warning');
    password.focus();
    return false;
  }
  $.ajax({
    url: '/user/member/loginpage',
    type: 'post',
    data: { member_id: member_id.value, password: password.value },
    async: false,
    dataType: 'json',
    success: function (data) {
      if (data) {
        console.log('data', data);

        document.forms['login-form'].submit();
      }
      if (data == false) {
        console.log('data', data);
        swal('', '아이디와 비밀번호를 확인해주세요.', 'warning');
        password.focus();
        return false;
      }
    },
  });
}
