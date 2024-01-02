// 상태 변경 함수!
function changeState(state) {
  let stateCompleteVerify = true;
  //완료된것은 상태변경 못하게 예외처리
  $("input[name=checkbox]:checked").each(function () {
    if ($(this).closest("tr").find("span[name='state']")[0].innerText == "완료") {
      stateCompleteVerify = false;
      return false;
    }
  });

  if (stateCompleteVerify) {
    let formData = null;

    if (state == "Check") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("cancellation_return_no", $(this).val());
      });
    } else if (state == "Complete") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("cancellation_return_no", $(this).val());
      });
    }
    formData.append("state", state);

    $.ajax({
      url: "/admin/order/return/changeStateIn" + state,
      type: "POST",
      data: formData,
      enctype: "multipart/form-data",
      contentType: false,
      processData: false,
      //dataType: "JSON",
      async: false,
      success: function (data) {
        if (data.failList == null) {
          //전부 성공
        } else {
          // 실패한것들 뿌려주고 다시 시도하라고 안내 ( 시즌2 )
        }
        getData();
      },
      error: function (error) {
        console.log(error.responseJSON);
        exceptionToastr(error.responseJSON);
      },
    });
  } else {
    detailErrorToastr("stateCompleteVerify");
  }
}

// 검색 AJAX 호출 함수
function getData() {
  let formData = new FormData(document.getElementById("searchForm"));
  formData.append("pageNo", pageNo);

  $.ajax({
    url: "/admin/order/return/search",
    type: "POST",
    data: formData,
    enctype: "multipart/form-data",
    contentType: false,
    processData: false,
    //dataType: "JSON",
    async: false,
    success: function (data) {
      // 정보 뷰단에 뿌려주는 함수
      outputReturnList(data.returnList);
      // 페이지네이션 뷰단에 output 함수
      outputPagination(data.pagination);
      checkAllFalse();
    },
    error: function (error) {
      console.log(error.responseJSON);
      exceptionToastr(error.responseJSON);
    },
  });
}

// 초기화 버튼 함수
function clickReset() {
  $(":radio[name='stateSelect'][value='전체']").prop("checked", true);
  $(":radio[name='negligence'][value='']").prop("checked", true);
  $("#date-select").val("").prop("selected", true);
  $("#date-range").data("daterangepicker").setStartDate(startDate);
  $("#date-range").data("daterangepicker").setEndDate(endDate);
  $("#wordSelect").val("").prop("selected", true);
  $("#searchText").prop("value", "");
  $("select").selectpicker("refresh");

  getData();
}

// returnList 아웃풋 하는 함수
function outputReturnList(returnList) {
  let output = "";
  $.each(returnList, function (i, returnData) {
    let order_day = changeDateForm(returnData.order_day);
    let application_date = changeDateForm(returnData.application_date);
    let check_date = changeDateForm(returnData.check_date);
    let complete_date = changeDateForm(returnData.complete_date);

    let negligence = null;
    if (returnData.negligence == "Y") {
      negligence = "판매자";
    } else if (returnData.negligence == "N") {
      negligence = "구매자";
    }

    let reason = "없음";
    if (returnData.reason != null) {
      reason = returnData.reason;
    }

    output += `<tr><td><div class="custom-control custom-checkbox ml-2">`;
    output += `<input type="checkbox" id="customCheckBox${i}" required="" class="custom-control-input checkbox" name="checkbox" value="${returnData.cancellation_return_no}"/>`;
    output += `<label class="custom-control-label" for="customCheckBox${i}"></label></div></td>`;
    output += `<td>${returnData.merchant_uid}</td>`;
    if (returnData.state == "신청") {
      output += `<td><span class="badge badge-danger light" name="state">${returnData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (returnData.state == "확인") {
      output += `<td><span class="badge badge-warning light" name="state">${returnData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (returnData.state == "완료") {
      output += `<td><span class="badge badge-info light" name="state">${returnData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${returnData.member_id}</td>`;
    output += `<td>${returnData.member_name}</td>`;
    output += `<td><div class="d-flex align-items-center"><img src="${returnData.product_main_image}" class="rounded-lg mr-2" width="60" alt=""/><span class="w-space-no">${returnData.product_name}</span></div></td>`;
    output += `<td>${returnData.cancel_money}</td>`;
    output += `<td>${returnData.quantity}</td>`;
    output += `<td>${returnData.discounted_price}</td>`;
    output += `<td>${order_day}</td>`;
    output += `<td>${application_date}</td>`;
    output += `<td>${complete_date}</td>`;
    output += `<td>${negligence}</td>`;
    output += `<td>${reason}</td></tr>`;
  });

  $("#inputReturnList").html(output);
}
