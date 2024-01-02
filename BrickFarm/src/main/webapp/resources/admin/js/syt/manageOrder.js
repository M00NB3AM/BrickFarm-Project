// 상태 변경 함수!
function changeState(state) {
  // 상태변경 예외 처리(모달사용X)
  let completeActive = true;
  let stateCompleteVerify = true;
  // 입력창 예외 처리
  let inputVerify = true;
  let inputVerifyBefore = true;
  if (state == "afterPayment") {
    $("input[name=depositor_name]").each(function () {
      if ($(this).val() == "") {
        inputVerifyBefore = false;
        return false;
      }
    });
  } else if (state == "cancel" || state == "exchange" || state == "return") {
    $("input[name=reason]").each(function () {
      if ($(this).val() == "") {
        inputVerify = false;
        return false;
      }
    });
  } else {
    $("input[name=checkbox]:checked").each(function () {
      let payment_state = $(this).closest("tr").find("span[name=order_state]")[0].innerText;
      let delivery_state = $(this).closest("tr").find("span[name=delivery_state]")[0].innerText;
      let search_state = payment_state.split(")")[0].split("(")[1];

      if (state == "beforePayment") {
        if (payment_state == "구매확정" || search_state == "완료" || delivery_state == "배송완료") {
          stateCompleteVerify = false;
          return false;
        }
      } else if (state == "complete") {
        if (delivery_state != "배송완료") {
          completeActive = false;
          return false;
        } else if (payment_state == "구매확정" || search_state == "완료") {
          stateCompleteVerify = false;
          return false;
        }
      }
    });
  }

  if (inputVerify && inputVerifyBefore && completeActive && stateCompleteVerify) {
    let formData = null;

    if (state == "beforePayment" || state == "complete") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("detailed_order_no", $(this).val());
      });
    } else {
      formData = new FormData(document.getElementById("changeStateForm"));
    }

    formData.append("state", state);

    $.ajax({
      url: `/admin/order/manageOrder/changeStateIn${state}`,
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
  } else if (inputVerify == false) {
    detailErrorToastr("inputVerify");
  } else if (inputVerifyBefore == false) {
    detailErrorToastr("inputVerifyBefore");
  } else if (completeActive == false) {
    detailErrorToastr("completeActive");
  } else if (stateCompleteVerify == false) {
    detailErrorToastr("stateCompleteVerify");
  }
}

// 검색 AJAX 호출 함수
function getData() {

  let formData = new FormData(document.getElementById("searchForm"));
  formData.append("pageNo", pageNo);

  $.ajax({
    url: "/admin/order/manageOrder/search",
    type: "POST",
    data: formData,
    enctype: "multipart/form-data",
    contentType: false,
    processData: false,
    //dataType: "JSON",
    async: false,
    success: function (data) {
      // 정보 뷰단에 뿌려주는 함수
      outputOrderList(data.orderList);
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
  $(":radio[name='totalState'][value='']").prop("checked", true);
  $("#date-select").val("").prop("selected", true);
  $("#date-range").data("daterangepicker").setStartDate(startDate);
  $("#date-range").data("daterangepicker").setEndDate(endDate);
  $("#wordSelect").val("").prop("selected", true);
  $("#searchText").prop("value", "");
  $("select").selectpicker("refresh");

  getData();
}

// 데이터 output
function outputOrderList(orderList) {
  let output = "";
  $.each(orderList, function (i, order) {
    let order_day = changeDateForm(order.order_day);
    let deposit_time = changeDateForm(order.deposit_time);
    let complete_date = changeDateForm(order.complete_date);
    let delivery_waiting_date = changeDateForm(order.delivery_waiting_date);

    let post_no = "없음";
    if (order.post_no != null) {
      post_no = order.post_no;
    }
    // data-toggle="modal" data-target="#detailedModal" onclick="findDetail('${order.detailed_order_no}')"
    output += `<tr class="listAll">`;
    output += `<td class="stopEvent"><div class="custom-control custom-checkbox ml-2">`;
    output += `<input type="checkbox" id="customCheckBox${i}" required="" class="custom-control-input checkbox" name="checkbox" value="${order.detailed_order_no}"/>`;
    output += `<label class="custom-control-label" for="customCheckBox${i}"></label></div></td>`;
    output += `<td name="merchant_uid">${order.merchant_uid}</td>`;
    output += `<td>${order.member_id}</td>`;
    output += `<td name="member_name">${order.member_name}</td>`;
    output += `<td><div class="d-flex align-items-center"><img src="${order.product_main_image}" name="product_main_image" class="rounded-lg mr-2" width="60" alt=""/><span class="w-space-no" name="product_name">${order.product_name}</span></div></td>`;
    output += `<td name="discounted_price">${order.total_discounted_price}</td>`;
    output += `<td>${order_day}</td>`;
    if (order.state == "결제대기") {
      output += `<td><span class="badge badge-dark light" name="order_state">${order.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.state.search("신청") > 0) {
      output += `<td><span class="badge badge-danger light" name="order_state">${order.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.state == "결제완료" || order.state.search("확인") > 0 || order.state.search("진행중") > 0) {
      output += `<td><span class="badge badge-warning light" name="order_state">${order.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.state == "구매확정" || order.state.search("완료") > 0) {
      output += `<td><span class="badge badge-info light" name="order_state">${order.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${deposit_time}</td>`;
    output += `<td>${complete_date}</td>`;
    if (order.delivery_state == "결제대기") {
      output += `<td><span class="badge badge-dark light" name='delivery_state'>${order.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.delivery_state == "배송준비중") {
      output += `<td><span class="badge badge-danger light" name='delivery_state'>${order.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.delivery_state == "배송대기중" || order.delivery_state == "배송중") {
      output += `<td><span class="badge badge-warning light" name='delivery_state'>${order.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (order.delivery_state == "배송완료") {
      output += `<td><span class="badge badge-info light" name='delivery_state'>${order.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${delivery_waiting_date}</td>`;
    output += `<td>${post_no}</td>`;
  });
  $("#inputOrderList").html(output);
}

// 모달 오픈
function openModal(what) {
  if ($("input[name=checkbox]:checked").length == 0) {
    event.stopPropagation();
  }

  let stateCompleteVerify = true;
  let cancelActive = true;
  let exchangeReturnActive = true;
  let completeActive = true;

  // 상태변경 금지 예외처리
  $("input[name=checkbox]:checked").each(function () {
    let payment_state = $(this).closest("tr").find("span[name=order_state]")[0].innerText;
    let delivery_state = $(this).closest("tr").find("span[name=delivery_state]")[0].innerText;
    let search_state = payment_state.split(")")[0].split("(")[1];
    if (payment_state == "구매확정" || search_state == "완료") {
      stateCompleteVerify = false;
      return false;
    } else if (what == "cancel") {
      if (delivery_state == "배송대기중" || delivery_state == "배송중" || delivery_state == "배송완료") {
        cancelActive = false;
        return false;
      }
    } else if (what == "exchange" || what == "return") {
      if (delivery_state != "배송완료") {
        exchangeReturnActive = false;
        return false;
      }
    }
  });

  if (stateCompleteVerify && cancelActive && exchangeReturnActive && completeActive) {
    let output = `<table class="table table-sm mb-0"><thead><tr>`;
    output += `<th>주문번호<input type="hidden" id="modalWhat" value="${what}" /></th>`;
    output += `<th>주문자</th>`;
    output += `<th>상품명</th>`;
    if (what == "afterPayment") {
      output += `<th>구매가격</th>`;
      output += `<th>입금자명</th>`;
    } else {
      if (what != "exchange") {
        output += `<th>신청금액</th>`;
        output += `<th>과실책임</th>`;
      } else {
        output += `<th>구매가격</th>`;
      }
      output += `<th>사유</th>`;
    }
    output += `</tr></thead>`;

    $("input[name=checkbox]:checked").each(function () {
      let detailed_order_no = $(this).val();
      let merchant_uid = $(this).closest("tr").find("td[name='merchant_uid']")[0].innerText;
      let product_main_image = $(this).closest("tr").find("img[name='product_main_image']")[0].currentSrc;
      let member_name = $(this).closest("tr").find("td[name='member_name']")[0].innerText;
      let product_name = $(this).closest("tr").find("span[name='product_name']")[0].innerText;
      let discounted_price = $(this).closest("tr").find("td[name='discounted_price']")[0].innerText;
      let cancel_money = Number(discounted_price.replace(",", ""));

      output += `<tbody>`;
      output += `<tr class="btn-reveal-trigger"><input type="hidden" name="detailed_order_no" value="${detailed_order_no}" />`;
      output += `<td>${merchant_uid}</td>`;
      output += `<td>${member_name}</td>`;
      output += `<td><div class="d-flex align-items-center"><img src="${product_main_image}" class="rounded-lg mr-2" width="96" alt=""/><span class="w-space-no">${product_name}</span></div></td>`;
      if (what == "afterPayment") {
        output += `<td>${discounted_price}</td>`;
        output += `<td><input type="text" name="depositor_name" class="col form-control input-default" placeholder="입금자명을 입력하세요." value="" /></td>`;
      } else {
        if (what == "return") {
          output += `<td><input type="hidden" name="cancel_money" value="${cancel_money}" />${discounted_price}</td>`;
          output += `<td><select class="form-control" name="negligence"><option value="Y">판매자</option><option value="N">구매자</option></select></td>`;
        } else if (what == "cancel") {
          output += `<td><input type="hidden" name="cancel_money" value="${cancel_money}" />${discounted_price}</td>`;
          output += `<td><select class="form-control" name="negligence"><option value="N">구매자</option></select></td>`;
        } else if (what == "exchange") {
          output += `<td>${discounted_price}</td>`;
        }
        output += `<td><input type="text" name="reason" class="col form-control input-default" placeholder="사유를 입력하세요." value="" /></td>`;
      }
      output += `</tr></tbody>`;
    });
    output += `</table>`;

    $("#modalInput").html(output);
  } else if (stateCompleteVerify == false) {
    detailErrorToastr("stateCompleteVerify");
    event.stopPropagation();
  } else if (cancelActive == false) {
    detailErrorToastr("cancel");
    event.stopPropagation();
  } else if (exchangeReturnActive == false) {
    detailErrorToastr("exchangeReturn");
    event.stopPropagation();
  } else if (completeActive == false) {
    detailErrorToastr("completeActive");
    event.stopPropagation();
  }
}

// 디테일 모달 정보 받아오기
function findDetail(detailed_order_no) {
  $.ajax({
    url: "/admin/order/manageOrder/detail",
    type: "POST",
    data: { detailed_order_no: detailed_order_no },
    async: false,
    success: function (data) {
      outputDetailedModal(data.orderDetailList);
      $("#detailedModal").modal("show");
    },
    error: function (error) {
      console.log(error.responseJSON);
      exceptionToastr(error.responseJSON);
    },
  });
}

// 디테일 모달 정보 출력
function outputDetailedModal(orderDetailList) {
  let output = "";
  $.each(orderDetailList, function (i, detailData) {
    let total_complete_date = changeDateForm(detailData.total_complete_date);
    let memo = "없음";
    if (detailData.memo != null) {
      memo = detailData.memo;
    }

    output += `<tr class="btn-reveal-trigger">`;
    output += `<td>${detailData.detailed_order_no}</td>`;
    output += `<td><div class="d-flex align-items-center"><img src="${detailData.product_main_image}" class="rounded-lg mr-2" width="96" alt=""/><span class="w-space-no">${detailData.product_name}</span></div></td>`;
    output += `<td>${detailData.product_price}</td>`;
    output += `<td>${detailData.event_product_price}</td>`;
    output += `<td>${detailData.discounted_price}</td>`;
    output += `<td>${detailData.quantity}</td>`;
    if (detailData.total_state == "진행중") {
      output += `<td><span class="badge badge-warning light">${detailData.total_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (detailData.total_state == "완료") {
      output += `<td><span class="badge badge-info light">${detailData.total_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${total_complete_date}</td>`;
    output += `<td>${memo}</td>`;
    output += `</tr>`;
  });

  $("#detailedModalInput").html(output);
}
