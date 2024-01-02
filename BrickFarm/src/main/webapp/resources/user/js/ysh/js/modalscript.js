function generateSessionId(){

return 'new_session_id' + Math.floor(Math.random() * 100000);
}

var newSessionId = generateSessionId();



$(function(){
    $('#modalbody1').show();
    $('#beforediv').show();
    $('#afterdiv').hide();
    $('#modalspan1').show();
    $('#modalspan2').hide();
    $('#footmsg1').hide();
    $('#footmsg2').hide();
    
})

let number = "";
//이메일 인증번호 받는 함수
function emailconfirm(){
    let email = document.getElementById('modalemail');
    if(email.value == ""){
        swal("", "이메일을 입력해주세요.", "error");
        return false;        
    }else if(email.value != ""){
    $.ajax({
        url:"/find",
        type : "post",
        data : {email : email.value},
        async : false,
        dataType : "json",
        success : function(data){
            console.log(data) // 인증번호 넘어옴.
            alert('메일 발송');
            number = data;
            if(data != null){
                $('#beforemodalemail').val("");
                $('#beforediv').hide();
                $('#afterdiv').show();
                $('#modalspan1').hide();
                $('#modalspan2').show();
                $('#footmsg1').hide();
                $('#footmsg2').hide();
                
            }else{
                alert('인증번호 발송에 실패했습니다.');
            }
        },
        error : function(error){
            console.log(error);
        }
    });    
    }
}

function confirm(){
    let emailnum = document.getElementById('emailnum');
    let modalemail = document.getElementById('modalemail');
    console.log(number);
    if(number == emailnum.value){ // 인증번호가 같다면
        $('#footmsg2').show();
    }else if(number != emailnum.value){ // 같지 않다면
        $('#footmsg1').show();
    }        
}

function complite(){
    let modalemail = document.getElementById('modalemail');
        email.value = modalemail.value;
}


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
        type : 'get',
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

function inputdata(data) {
  console.log(data.name);
  console.log(data.phone);
  if ($("#phone_number").value != "") {
    $("#phone_number").val(data.response.phone);
    $("#phone_number").prop("type", "text");
  }
  if ($("#member_name").value != "") {
    $("#member_name").val(data.response.name);
    $("#member_name").prop("type", "text");
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