// 상태 변경 함수!
function changeState(state) {
  let stateCompleteVerify = true;
  let postNoVerify = true;
  let stateProcessVerify = true;
  if (state == "Process") {
    $("input[name=post_number]").each(function () {
      if ($(this).val() == "") {
        postNoVerify = false;
        return false;
      }
    });
  } else if(state == "Complete") {
    $("input[name=checkbox]:checked").each(function () {
    	if ($(this).closest("tr").find("span[name=state]")[0].innerText != "진행중") {
       		stateProcessVerify = false;
       		return false;
     	}
    })
  } else {
    $("input[name=checkbox]:checked").each(function () {
      if ($(this).closest("tr").find("span[name=state]")[0].innerText == "완료") {
        stateCompleteVerify = false;
        return false;
      }
    }); 
  }

  if (stateCompleteVerify && postNoVerify && stateProcessVerify) {
    let formData = null;

    if (state == "Check") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("exchange_no", $(this).val());
      });
    } else if (state == "Process") {
      formData = new FormData(document.getElementById("processForm"));
    } else if (state == "Complete") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("exchange_no", $(this).val());
      });
    }

    formData.append("state", state);

    $.ajax({
      url: `/admin/order/exchange/changeStateIn${state}`,
      type: "POST",
      data: formData,
      enctype: "multipart/form-data",
      contentType: false,
      processData: false,
      //dataType: "JSON",
      async: false,
      success: function (data) {
        getData();
      },
      error: function (error) {
        console.log(error.responseJSON);
        exceptionToastr(error.responseJSON);
      },
    });
  } else if (stateCompleteVerify == false) {
    detailErrorToastr("stateCompleteVerify");
  } else if (postNoVerify == false) {
    detailErrorToastr("postNoVerify");
  } else if (stateProcessVerify == false) {
    detailErrorToastr("stateProcessVerify");
  }
}

// 검색 AJAX 호출 함수
function getData() {
  let formData = new FormData(document.getElementById("searchForm"));
  formData.append("pageNo", pageNo);

  $.ajax({
    url: "/admin/order/exchange/search",
    type: "POST",
    data: formData,
    enctype: "multipart/form-data",
    contentType: false,
    processData: false,
    //dataType: "JSON",
    async: false,
    success: function (data) {
      // 정보 뷰단에 뿌려주는 함수
      outputExchangeList(data.exchangeList);
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
  $("#date-select").val("").prop("selected", true);
  $("#date-range").data("daterangepicker").setStartDate(startDate);
  $("#date-range").data("daterangepicker").setEndDate(endDate);
  $("#wordSelect").val("").prop("selected", true);
  $("#searchText").prop("value", "");
  $("select").selectpicker("refresh");

  getData();
}

// 데이터 output
function outputExchangeList(exchangeList) {
  let output = "";
  $.each(exchangeList, function (i, exchange) {
    let order_day = changeDateForm(exchange.order_day);
    let application_date = changeDateForm(exchange.application_date);
    let check_date = changeDateForm(exchange.check_date);
    let process_date = changeDateForm(exchange.process_date);
    let complete_date = changeDateForm(exchange.complete_date);

    let post_number = "없음";
    if (exchange.post_number != null) {
      post_number = exchange.post_number;
    }

    let reason = "없음";
    if (exchange.reason != null) {
      reason = exchange.reason;
    }

    output += `<tr><td><div class="custom-control custom-checkbox ml-2">`;
    output += `<input type="checkbox" id="customCheckBox${i}" class="custom-control-input checkbox" name="checkbox" value="${exchange.exchange_no}" required=""/>`;
    output += `<label class="custom-control-label" for="customCheckBox${i}"></label></div></td>`;
    output += `<td name="merchant_uid">${exchange.merchant_uid}</td>`;
    output += `<td>${post_number}</td>`;
    if (exchange.state == "신청") {
      output += `<td><span class="badge badge-danger light" name="state">${exchange.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (exchange.state == "확인" || exchange.state == "진행중") {
      output += `<td><span class="badge badge-warning light" name="state">${exchange.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (exchange.state == "완료") {
      output += `<td><span class="badge badge-info light" name="state">${exchange.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${exchange.member_id}</td>`;
    output += `<td name="member_name">${exchange.member_name}</td>`;
    output += `<td><div class="d-flex align-items-center"><img src="${exchange.product_main_image}" class="rounded-lg mr-2" name="product_main_image" width="60" alt=""/><span class="w-space-no" name="product_name">${exchange.product_name}</span></div></td>`;
    output += `<td>${exchange.discounted_price}</td>`;
    output += `<td name="quantity">${exchange.quantity}</td>`;
    output += `<td>${order_day}</td>`;
    output += `<td>${application_date}</td>`;
    output += `<td>${process_date}</td>`;
    output += `<td>${complete_date}</td>`;
    output += `<td>${reason}</td></tr>`;
  });

  $("#inputExchangeList").html(output);
}

//체크박스 확인 진행중에 output
function openChangeProcess() {
  if ($("input[name=checkbox]:checked").length == 0) {
    event.stopPropagation();
  }

  let stateCompleteVerify = true;
  $("input[name=checkbox]:checked").each(function () {
    if ($(this).closest("tr").find("span[name='state']")[0].innerText == "완료") {
      stateCompleteVerify = false;
      return false;
    }
  });

  if (stateCompleteVerify) {
    let output = "";
    $("input[name=checkbox]:checked").each(function () {
      let exchange_no = $(this).val();
      let merchant_uid = $(this).closest("tr").find("td[name='merchant_uid']")[0].innerText;
      let member_name = $(this).closest("tr").find("td[name='member_name']")[0].innerText;
      let product_main_image = $(this).closest("tr").find("img[name='product_main_image']")[0].currentSrc;
      let product_name = $(this).closest("tr").find("span[name='product_name']")[0].innerText;
      let quantity = $(this).closest("tr").find("td[name='quantity']")[0].innerText;

      output += `<tr class="btn-reveal-trigger"><input type="hidden" name="exchange_no" value="${exchange_no}" />`;
      output += `<td>${merchant_uid}</td>`;
      output += `<td>${member_name}</td>`;
      output += `<td><div class="d-flex align-items-center"><img src="${product_main_image}" class="rounded-lg mr-2" width="96" alt=""/><span class="w-space-no">${product_name}</span></div></td>`;
      output += `<td>${quantity}</td>`;
      output += `<td><input type="text" name="post_number" class="col form-control input-default" placeholder="운송장번호를 입력하세요." /></td>`;
      output += `</tr>`;
    });
    $("#modalInput").html(output);
  } else {
    detailErrorToastr("stateCompleteVerify");
    event.stopPropagation();
  }
}
