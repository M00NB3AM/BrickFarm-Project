let isDuplicate = false;
var regId = /^[가-힣A-Za-z0-9]{4,8}$/;
var regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,10}$/;
var regName = /^[가-힣a-zA-Z]{2,15}$/;
var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
var pattern = /\s/g; //공백확인

$(function () {
  $('#member_id').change(function () {
    if (isDuplicate) {
      isDuplicate = false;
    }
  });
});

//아이디 중복검사..
function checkUserId() {
  let member_id = $('#member_id');
  if (member_id.val() == '') {
    swal('', '아이디를 입력해주세요.', 'warning');
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.val())) {
    swal('', '아이디는 4 ~ 8자 영문 대소문자, 숫자, 한글만 입력가능합니다.', 'warning');
    member_id.focus();
    return false;
  } else if (pattern.test(member_id.val())) {
    swal('', '공백은 사용하실수 없습니다.', 'warning');
    member_id.focus();
    return false;
  }
  $.ajax({
    url: '/user/member/checkUserid',
    type: 'post',
    data: { member_id: member_id.val() },
    async: false,
    success: function (data) {
      console.log(data);
      if (data == 'duplicate') {
        swal('', '중복된 아이디입니다.', 'error');
        isDuplicate = false;
        member_id.focus();
      } else {
        swal('', '사용가능한 아이디입니다.', 'success');
        isDuplicate = true;
      }
    },
  });
}

//유저 회원가입 부분
function registerMember() {
  console.log('회원가입유효성검사.');
  //유효성검사
  var member_id = document.getElementById('member_id');
  var userId_confirm = document.getElementById('userId_confirm');
  var password = document.getElementById('password');
  var password_confirm = document.getElementById('password_confirm');
  var member_name = document.getElementById('member_name');
  var phone_number = document.getElementById('phone_number');
  var gender = document.getElementById('gender');
  var email = document.getElementById('email');
  var birth_date = document.getElementById('birth_date');
  var zip_code = document.getElementById('zip_code');
  var address = document.getElementById('address');

  if (address == '') {
    registershakeModal();
    swal('', '주소입력을 부탁드립니다.', 'info');
    address.focus();
    return false;
  }

  if (phone_number && member_name == '') {
    registershakeModal();
    swal('', '본인인증을 부탁드립니다.', 'warning');
    return false;
  }
  //id 확인.
  if (member_id.value == '') {
    registershakeModal();
    swal('', '아이디를 입력하세요.', 'warning');
    member_id.focus();
    return false;
  } else if (!regId.test(member_id.value)) {
    registershakeModal();
    swal('', '아이디는 4 ~ 8자 영문 대소문자, 숫자, 한글만 입력가능합니다.', 'warning');
    member_id.focus();
    return false;
  }
  if (!isDuplicate) {
    swal('', '중복검사를 부탁드립니다.', 'warning');
    member_id.focus();
    return false;
  }
  //비밀번호 확인.
  if (password.value == '') {
    registershakeModal();
    swal('', '비밀번호를 입력하세요.', 'warning');
    password.focus();
    return false;
    //아이디와 비밀번호가 같은지.
  } else if (password.value == member_id.value) {
    registershakeModal();
    swal('', '아이디와 동일한 비밀번호를 사용할 수 없습니다.', 'warning');
    password.focus();
    return false;
    //비밀번호 자리수 검사
  } else if (!regPwd.test(password.value)) {
    registershakeModal();
    swal('', '비밀번호는 8자리~10자리만 가능합니다.', 'warning');
    password.focus();
    return false;
  }
  //비밀번호 더블체크
  if (password_confirm.value !== password.value) {
    registershakeModal();
    swal('', '비밀번호가 동일하지 않습니다.', 'warning');
    password_confirm.focus();
    return false;
  }
  //성별 확인.
  if (gender.value == '') {
    registershakeModal();
    swal('', '본인인증을 해주세요.', 'warning');
    gender.focus();
    return false;
  }
  //이메일주소 확인.
  if (email.value.length == 0) {
    registershakeModal();
    swal('', '이메일을 입력해주세요.', 'warning');
    email.focus();
    return false;
  } else if (!regEmail.test(email.value)) {
    registershakeModal();
    swal('', '잘못된 이메일형식입니다.', 'warning');
    email.focus();
    return false;
  }

  //이름 확인. 한글과 영어만 확인.
  if (member_name.value == '') {
    registershakeModal();
    swal('', '본인인증을 부탁드립니다.', 'warning');
    member_name.focus();
    return false;
  } else if (!regName.test(member_name.value)) {
    registershakeModal();
    swal('', '본인인증을 부탁드립니다.', 'warning');
    member_name.focus();
    return false;
  }

  //유효성에 문제가 없다면 submit
  document.forms['registerform'].submit();

  //생일은 전화번호 인증 이메일에서 상대방의 생일을 가져올 수 있지 않을까..?
}
//회원가입 유효성 검사에 통과하지 못했을 경우의 이벤트
function registershakeModal() {
  $('#loginModal .modal-dialog').addClass('shake');
  swal('', '아이디와 비밀번호를 확인해주세요.', 'error');

  setTimeout(function () {
    $('#loginModal .modal-dialog').removeClass('shake');
  }, 1000);
}


