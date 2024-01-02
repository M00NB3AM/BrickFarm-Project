// 상태 변경 함수!
function changeState(state) {
  let stateCompleteVerify = true;
  let postNoVerify = true;
  if (state == "deliverywait") {
    $("input[name=post_number]").each(function () {
      if ($(this).val() == "") {
        postNoVerify = false;
        return false;
      }
    });
  } else {
    $("input[name=checkbox]:checked").each(function () {
      let delivery_state = $(this).closest("tr").find("span[name='delivery_state']")[0].innerText;
      let total_state = $(this).closest("tr").find("span[name='total_state']")[0].innerText;
      if (delivery_state == "배송완료" || delivery_state == "배송중" || total_state == "완료") {
        stateCompleteVerify = false;
        return false;
      }
    });
  }

  if (stateCompleteVerify && postNoVerify) {
    let formData = null;

    if (state == "paymentwait") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("merchant_uid", $(this).val());
      });
    } else if (state == "prepare") {
      formData = new FormData();
      $("input[name=checkbox]:checked").each(function () {
        formData.append("merchant_uid", $(this).val());
      });
    } else if (state == "deliverywait") {
      formData = new FormData(document.getElementById("deliveryWaitForm"));
    }

    formData.append("state", state);
	formData.get("merchant_uid");
	formData.get("state");
    $.ajax({
      url: `/admin/order/delivery/changeState`,
      type: "POST",
      data: formData,
      enctype: "multipart/form-data",
      contentType: false,
      processData: false,
      //dataType: "TEXT",
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
    deliveryErrorToastr();
  } else if (postNoVerify == false) {
    detailErrorToastr("postNoVerify");
  }
}

// 검색 AJAX 호출 함수
function getData() {
  let formData = new FormData(document.getElementById("searchForm"));
  formData.append("pageNo", pageNo);

  $.ajax({
    url: "/admin/order/delivery/search",
    type: "POST",
    data: formData,
    enctype: "multipart/form-data",
    contentType: false,
    processData: false,
    //dataType: "JSON",
    async: false,
    success: function (data) {
      // 정보 뷰단에 뿌려주는 함수
      outputDeliveryList(data.deliveryList);
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

// 데이터 출력
function outputDeliveryList(deliveryList) {
  let output = "";
  $.each(deliveryList, function (i, delivery) {
    let order_day = changeDateForm(delivery.order_day);
    let deposit_time = changeDateForm(delivery.deposit_time);
    let delivery_waiting_date = changeDateForm(delivery.delivery_waiting_date);
    let total_complete_date = changeDateForm(delivery.total_complete_date);

    let post_no = "없음";
    if (delivery.post_no != null) {
      post_no = delivery.post_no;
    }

    let memo = "없음";
    if (delivery.memo != null) {
      memo = delivery.memo;
    }

    output += `<tr class="listAll">`;
    output += `<td class="stopEvent"><div class="custom-control custom-checkbox ml-2">`;
    output += `<input type="checkbox" id="customCheckBox${i}" required="" class="custom-control-input checkbox" name="checkbox" value="${delivery.merchant_uid}"/>`;
    output += `<label class="custom-control-label" for="customCheckBox${i}"></label></div></td>`;
    output += `<td name="merchant_uid">${delivery.merchant_uid}</label></td>`;
    output += `<td><input type="hidden" value="${memo}" name="memo">${post_no}</td>`;
    output += `<td name="member_id">${delivery.member_id}</td>`;
    output += `<td name="member_name">${delivery.member_name}</td>`;
    output += `<td name="total_pay_money">${delivery.total_pay_money}</td>`;
    output += `<td>${order_day}</td>`;
    output += `<td>${deposit_time}</td>`;
    if (delivery.delivery_state == "결제대기") {
      output += `<td><span class="badge badge-dark light" name="delivery_state">${delivery.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (delivery.delivery_state == "배송준비중") {
      output += `<td><span class="badge badge-danger light" name="delivery_state">${delivery.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (delivery.delivery_state == "배송대기중" || delivery.delivery_state == "배송중") {
      output += `<td><span class="badge badge-warning light" name="delivery_state">${delivery.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (delivery.delivery_state == "배송완료") {
      output += `<td><span class="badge badge-info light" name="delivery_state">${delivery.delivery_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${delivery_waiting_date}</td>`;
    if (delivery.total_state == "진행중") {
      output += `<td><span class="badge badge-warning light" name="total_state">${delivery.total_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (delivery.total_state == "완료") {
      output += `<td><span class="badge badge-info light" name="total_state">${delivery.total_state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${total_complete_date}</td>`;
  });
  $("#inputDeliveryList").html(output);
}

//체크박스 확인 모달 출력
function openChangeDeliveryWait() {
  if ($("input[name=checkbox]:checked").length == 0) {
    event.stopPropagation();
  }

  let stateCompleteVerify = true;
  $("input[name=checkbox]:checked").each(function () {
    let delivery_state = $(this).closest("tr").find("span[name='delivery_state']")[0].innerText;
    let total_state = $(this).closest("tr").find("span[name='total_state']")[0].innerText;
    if (delivery_state == "배송완료" || delivery_state == "배송중" || total_state == "완료") {
      stateCompleteVerify = false;
      return false;
    }
  });

  if (stateCompleteVerify) {
    let output = "";
    $("input[name=checkbox]:checked").each(function () {
      let merchant_uid = $(this).val();
      let memo = $(this).closest("tr").find("input[name=memo]")[0].value;
      let member_id = $(this).closest("tr").find("td[name=member_id]")[0].innerText;
      let member_name = $(this).closest("tr").find("td[name=member_name]")[0].innerText;
      let total_pay_money = $(this).closest("tr").find("td[name=total_pay_money]")[0].innerText;

      output += `<tr class="btn-reveal-trigger"><input type="hidden" name="merchant_uid" value="${merchant_uid}" />`;
      output += `<td>${merchant_uid}</td>`;
      output += `<td>${member_id}</td>`;
      output += `<td>${member_name}</td>`;
      output += `<td>${total_pay_money}</td>`;
      output += `<td>${memo}</td>`;
      output += `<td><input type="text" name="post_number" class="col form-control input-default" placeholder="운송장번호를 입력하세요." /></td>`;
      output += `</tr>`;
    });
    $("#modal").modal("show");
    $("#modalInput").html(output);
  } else if (stateCompleteVerify == false) {
    deliveryErrorToastr();
    event.stopPropagation();
  }
}

// 디테일 모달 정보 얻기
function findDetail(merchant_uid) {
  $.ajax({
    url: "/admin/order/delivery/detail",
    type: "POST",
    data: { merchant_uid: merchant_uid },
    async: false,
    success: function (data) {
      outputDetailedModal(data.deliveryDetailList);
      $("#detailedModal").modal("show");
    },
    error: function (error) {
      console.log(error.responseJSON);
      exceptionToastr(error.responseJSON);
    },
  });
}

// 디테일 모달 정보 출력
function outputDetailedModal(deliveryDetailList) {
  let output = "";
  $.each(deliveryDetailList, function (i, detailData) {
    let complete_date = changeDateForm(detailData.complete_date);

    output += `<tr>`;
    output += `<td>${detailData.detailed_order_no}</td>`;
    output += `<td><div class="d-flex align-items-center"><img src="${detailData.product_main_image}" class="rounded-lg mr-2" width="96" alt=""/><span class="w-space-no">${detailData.product_name}</span></div></td>`;
    output += `<td>${detailData.discounted_price}</td>`;
    output += `<td>${detailData.quantity}</td>`;
    if (detailData.state == "결제대기") {
      output += `<td><span class="badge badge-dark light">${detailData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (detailData.state.search("신청") > 0) {
      output += `<td><span class="badge badge-danger light">${detailData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (detailData.state == "결제완료" || detailData.state.search("확인") > 0 || detailData.state.search("진행중") > 0) {
      output += `<td><span class="badge badge-warning light">${detailData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    } else if (detailData.state == "구매확정" || detailData.state.search("완료") > 0) {
      output += `<td><span class="badge badge-info light">${detailData.state}<span class="ml-1 fa fa-check"></span></span></td>`;
    }
    output += `<td>${complete_date}</td>`;
    output += `<td>${detailData.recipient_address}</td>`;
    output += `</tr>`;
  });

  $("#detailedModalInput").html(output);
}
