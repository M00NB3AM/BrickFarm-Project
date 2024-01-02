document.addEventListener('keyup', function(event) {
            if (event.key === 'Enter') {
                login();
            }
        });


// 관리자 로그인
function login() {
  let admin_id = document.getElementById("adminid");
  let password = document.getElementById("password");

  if (admin_id.value == "") {
    swal("", "아이디를 입력해주세요.", "warning");
    admin_id.focus();
    return false;
  }
  if (password.value == "") {
    swal("", "비밀번호를 입력해주세요.", "warning");
    admin_id.focus();
    return false;
  }

  $.ajax({
    url: "/admin/adminglogincheck",
    type: "post",
    data: {admin_id: admin_id.value, admin_password: password.value},
    async: false,
    dataType: "json",
    success: function (data) {
      if (data == true) {
        console.log("data", data);
        document.forms["loginform"].submit();
      } else {
        console.log("data", data);
        swal("", "아이디와 비밀번호를 확인해주세요.", "warning");
        password.focus();
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
}



