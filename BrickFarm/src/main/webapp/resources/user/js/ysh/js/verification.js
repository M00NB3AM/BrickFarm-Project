var IMP = window.IMP;      // 생략 가능
IMP.init("{imp74035534}"); // 예: imp00000000

// IMP.certification(param, callback) 호출
  IMP.certification({ // param
    // 주문 번호
    pg:'A010002002',//본인인증 설정이 2개이상 되어 있는 경우 필
    merchant_uid: "ORD20180131-0000011", 
    // 모바일환경에서 popup:false(기본값) 인 경우 필수
    m_redirect_url : "localhost://8081", 
    // PC환경에서는 pop up 파라미터가 무시되고 항상 true 로 적용됨
    popup : false 
  }, function (rsp) { // callback
    if (rsp.success) {
            console.log(rsp);
    } else {
      console.log("인증실패", rsp);
      // 인증 실패 시 로직,
    }
  });
  
  
 