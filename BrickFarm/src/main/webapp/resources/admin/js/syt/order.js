// --------전역변수 -----
let pageNo = 1;
const endDate = new Date();
const startDate = new Date(endDate - 1000 * 60 * 60 * 24 * 7);
// ----------전역변수 -----------

// 검색누르면 페이지 번호 바꾸고 데이터 받아오기
function clickSearch() {
  pageNo = 1;
  getData();
}

// 날짜타입 변환하는 함수 year:"2-digit"
function changeDateForm(date) {
  if (date != null) {
    let changeDate = new Date(date);
    return changeDate.toLocaleDateString("ko-KR");
    //+ "<br />" + changeDate.toLocaleTimeString("it-IT", { hour12: false });
  } else {
    return "없음";
  }
}
// 체크올 체크 풀기
function checkAllFalse() {
  $("#checkAll").prop("checked", false);
}

// 페이지네이션 output
function outputPagination(pagination) {
  let output = `<ul class="pagination pagination-sm pagination-gutter pagination-info">`;
  if (pagination.pageGroupsOfCurrentPage > 1) {
    output += `<li class="page-item page-indicator"><a class="page-link" value="${
      pagination.startNumOfPageGroups - 1
    }"><i class="la la-angle-left" value="${pagination.startNumOfPageGroups - 1}"></i></a></li>`;
  }
  for (let i = pagination.startNumOfPageGroups; i <= pagination.endNumOfPageGroups; i++) {
    output += `<li class="page-item"><a class="page-link" value="${i}">${i}</a></li>`;
  }
  if (pagination.pageGroupsOfCurrentPage < pagination.totalPageGroupsCount) {
    output += `<li class="page-item page-indicator"><a class="page-link" value="${
      pagination.endNumOfPageGroups + 1
    }"><i class="la la-angle-right" value="${pagination.endNumOfPageGroups + 1}"></i></a></li>`;
  }
  output += `</ul>`;
  
  checkAllFalse();
  $("#paginationInput").html(output);

  $("li[class=page-item]").each(function (e) {
    if ($(this).text() == pageNo) {
      $(this).attr("class", "page-item active");
    }
  });
}

//Toastr  -------------------------------------------------------------------

//배송 Toastr
function deliveryErrorToastr() {
  toastr.error("완료 또는 배송중 상태가 </br>아닌 정보로 시도해주세요.", "완료 또는 배송중 변경불가", {
    positionClass: "toast-top-full-width",
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    tapToDismiss: !1,
  });
}

//디테일 Toastr
function detailErrorToastr(state) {
  let errorMsg = "";
  let errorTitle = "";
  if (state == "stateCompleteVerify") {
    errorMsg = "완료상태가 아닌 정보로 </br>시도해주세요.";
    errorTitle = "완료상태 변경불가";
  } else if (state == "cancel") {
    errorMsg = "배송된 상품은 취소를 할수없습니다.";
    errorTitle = "취소 신청불가";
  } else if (state == "exchangeReturn") {
    errorMsg = "배송되지않은 상품은 교환/반품을 할수없습니다.";
    errorTitle = "교환/반품 신청불가";
  } else if (state == "completeActive") {
    errorMsg = "배송되지않은 상품은 구매확정을 할수없습니다.";
    errorTitle = "구매확정불가";
  } else if (state == "inputVerify") {
    errorMsg = "사유를 입력해주세요.";
    errorTitle = "사유정보 미입력";
  } else if (state == "inputVerifyBefore") {
    errorMsg = "입금자명을 입력해주세요.";
    errorTitle = "입금자명 미입력";
  } else if (state == "postNoVerify") {
    errorMsg = "운송장번호를 입력해주세요.";
    errorTitle = "운송장 미입력";
  } else if (state == "stateProcessVerify") {
    errorMsg = "먼저 진행중으로 변경해주세요.";
    errorTitle = "완료 상태 변경불가";
  }
  

  toastr.error(errorMsg, errorTitle, {
    positionClass: "toast-top-full-width",
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    tapToDismiss: !1,
  });
}

//exception Toastr
function exceptionToastr(err) {
console.log(err);
  let errorMsg = "exception 에러";
  let errorTitle = "코드 : " + err.errorCode + "</br> 메세지 : " + err.errorMessage + "</br> 날짜 : " + err.createdAt;

  toastr.error(errorMsg, errorTitle, {
    positionClass: "toast-top-full-width",
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    tapToDismiss: !1,
  });
}
// ------------------------------------------------------------------------------------
