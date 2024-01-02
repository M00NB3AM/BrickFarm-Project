// ajax 요청 시 넘기는 파라메터
const params = {
  curPageNo: 1,
  rowCountPerPage: 10,
  startDate: '',
  endDate: '',
  recentWeek: 1,
  isCheckedUsePoint: false,
  selectedPeriod: 0,
};

// 선택된 탭의 값을 각종 함수에서 통일하여 일괄적으로 관리하기 위해 참조되는 상수
const PERIOD = {
  DAILY: 0,
  WEEKLY: 1,
  MONTHLY: 2,
};

// 파라메터의 기간 값을 date-range-picker로부터 얻어와 초기화
function initDateParams() {
  const startDate = $('#dateRangePicker').val().split(' - ')[0];
  const endDate = $('#dateRangePicker').val().split(' - ')[1];
  setParams('startDate', startDate);
  setParams('endDate', endDate);
}

// 탭 이동시 date-range-picker 의 값을 탭의 성격에 맞게 초기화
function initDateParamsByCurrentValue() {
  let startDate, endDate;
  switch (params.selectedPeriod) {
    case PERIOD.DAILY:
      startDate = $('#dateRangePicker').val().split(' - ')[0];
      endDate = $('#dateRangePicker').val().split(' - ')[1];
      setParams('startDate', startDate);
      setParams('endDate', endDate);
      break;
    case PERIOD.WEEKLY:
      break;
    case PERIOD.MONTHLY:
      startDate = `${$('#startYearPicker').val()}-${(0 + $('#startMonthPicker').val()).slice(-2)}-01`;
      endDate = `${$('#endYearPicker').val()}-${(0 + $('#endMonthPicker').val()).slice(-2)}-01`;
      setParams('startDate', startDate);
      setParams('endDate', endDate);
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// 키, 값을 매개변수로 받아 파라메터 변수를 바꿔주는 Setter 함수
function setParams(key, value) {
  params[key] = value;
}

// 선택된 탭과 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  switch (params.selectedPeriod) {
    case PERIOD.DAILY:
      getDailyNetSalesInfo(params);
      break;
    case PERIOD.WEEKLY:
      getWeeklyNetSalesInfo(params);
      break;
    case PERIOD.MONTHLY:
      getMonthlyNetSalesInfo(params);
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// 일별 매출 기간 선택 핸들러
function dateRangePickerChangeHandler(e, picker) {
  const startDate = picker.startDate.format('YYYY-MM-DD');
  const endDate = picker.endDate.format('YYYY-MM-DD');
  setParams('startDate', startDate);
  setParams('endDate', endDate);
}

// 탭 선택 핸들러
function selectPeriod(value) {
  setParams('selectedPeriod', value);

  if (value == PERIOD.DAILY || value == PERIOD.MONTHLY) {
    initDateParamsByCurrentValue();
    // console.log(params);
  }
}

// 월별 매출 탭 - 시작 기간 선택 핸들러
function selectStartYearAndMonth() {
  const startYear = $('#startYearPicker').val();
  const startMonth = (0 + $('#startMonthPicker').val()).slice(-2);
  const startDate = `${startYear}-${startMonth}-01`;
  setParams('startDate', startDate);
}

// 월별 매출 탭 - 끝 기간 선택 핸들러
function selectEndYearAndMonth() {
  const endYear = $('#endYearPicker').val();
  const endMonth = (0 + $('#endMonthPicker').val()).slice(-2);
  const endDate = `${endYear}-${endMonth}-01`;
  setParams('endDate', endDate);
}

// 페이지 선택 핸들러
function selectPage(pageNo) {
  setParams('curPageNo', pageNo);
  search();
}

// 페이지당 보여줄 row의 개수 선택 핸들러
function selectRowCountPerPage(selectedValue) {
  setParams('rowCountPerPage', selectedValue);
  search();
}

// date-range-picker init
function initDateRangePicker() {
  let now = new Date();
  let before = new Date(now - 1000 * 60 * 60 * 24 * 7);

  /* https://www.daterangepicker.com/#options */

  $('.input-daterange-datepicker').daterangepicker({
    buttonClasses: ['btn', 'btn-sm'],
    applyClass: 'btn-danger',
    cancelClass: 'btn-inverse',
    startDate: before,
    endDate: now,
    locale: {
      format: 'YYYY-MM-DD',
      separator: ' - ',
      applyLabel: '선택',
      cancelLabel: '취소',
      daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
      monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    },
  });
}

/* === ajax ==================================================================================================================== */
// 일별 매출 데이터 api 요청 함수
function getDailyNetSalesInfo(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/statistics/netsales/daily',
    type: 'get',
    data: params,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printStatistics(data);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('검색 오류', '데이터를 검색하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 주별 매출 데이터 api 요청 함수
function getWeeklyNetSalesInfo(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/statistics/netsales/weekly',
    type: 'get',
    data: params,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printStatistics(data);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('검색 오류', '데이터를 검색하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 월별 매출 데이터 api 요청 함수
function getMonthlyNetSalesInfo(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/statistics/netsales/monthly',
    type: 'get',
    data: params,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printStatistics(data);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('검색 오류', '데이터를 검색하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

/* === print ==================================================================================================================== */
// 주별 매출 기간 select의 옵션 출력
function printRecentWeekSelect() {
  let output = '';
  for (let i = 1; i < 53; i++) {
    output += `<option value="${i}">${i}</option>`;
  }
  $('#recentWeek').html(output);
}

// 월별 매출 기간 연도 select의 옵션 출력
function printYearPickerkSelect() {
  let currentYear = new Date().getFullYear();

  let output = '';
  for (let i = currentYear - 10; i <= currentYear; i++) {
    if (i != currentYear) {
      output += `<option value="${i}">${i}</option>`;
    } else {
      output += `<option value="${i}" selected>${i}</option>`;
    }
  }
  $('#startYearPicker').html(output);
  $('#endYearPicker').html(output);
}

// 월별 매출 기간 월 select의 옵션 출력
function printMonthPickerSelect() {
  let currentMonth = new Date().getMonth() + 1;

  let output = '';
  for (let i = 1; i <= 12; i++) {
    if (i != currentMonth - 2) {
      output += `<option value="${i}">${i}</option>`;
    } else {
      output += `<option value="${i}" selected>${i}</option>`;
    }
  }
  $('#startMonthPicker').html(output);

  output = '';
  for (let i = 1; i <= 12; i++) {
    if (i != currentMonth) {
      output += `<option value="${i}">${i}</option>`;
    } else {
      output += `<option value="${i}" selected>${i}</option>`;
    }
  }
  $('#endMonthPicker').html(output);
}

// api 요청 후 넘어온 paginationInfo 값을 매개변수로 받아 그 값을 기준으로 페이지네이션 동적 출력
function printPagination(paginationInfo) {
  let output = '';

  if (paginationInfo.curBlockNo == 1) {
    output += `<a class="pagination_button previous disabled" id="previous">이전</a>`;
  } else {
    output += `<a class="pagination_button previous" id="previous" onclick="selectPage(${
      (paginationInfo.curBlockNo - 1) * paginationInfo.pageCountPerBlock - (paginationInfo.pageCountPerBlock - 1)
    });">이전</a>`;
  }

  output += `<span>`;
  for (let i = paginationInfo.startPageIndex; i <= paginationInfo.endPageIndex; i++) {
    if (paginationInfo.curPageNo == i) {
      output += `<a class="pagination_button current">${i}</a>`;
    } else {
      output += `<a class="pagination_button" onclick="selectPage(${i});">${i}</a>`;
    }
  }
  output += `</span>`;

  if (paginationInfo.curBlockNo < paginationInfo.totalPageBlockCount) {
    output += `<a class="pagination_button next" id="previous" onclick="selectPage(${
      (paginationInfo.curBlockNo + 1) * paginationInfo.pageCountPerBlock - (paginationInfo.pageCountPerBlock - 1)
    });">다음</a>`;
  } else {
    output += `<a class="pagination_button next disabled" id="previous">다음</a>`;
  }

  $('#pagination').html(output);
}

// 통계 데이터 ajax 요청 후 현재 선택된 탭에 맞춰 화면 출력 함수를 선택하여 실행시켜줄 함수
function printStatistics(data) {
  printPagination(data.paginationInfo);

  switch (params.selectedPeriod) {
    case PERIOD.DAILY:
      printDailyTable(data.dailyNetSales);
      setDailyNetSalesDetailChart(data.dailyNetSales, 'netSalesChart');
      break;
    case PERIOD.WEEKLY:
      printWeeklyTable(data.weeklyNetSales, data.currentWeekOfYear);
      setWeeklyNetSalesDetailChart(data.weeklyNetSales, 'netSalesChart', data.currentWeekOfYear);
      break;
    case PERIOD.MONTHLY:
      printMonthlyTable(data.monthlyNetSales);
      setMonthlyNetSalesDetailChart(data.monthlyNetSales, 'netSalesChart');
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// [일별매출] searchResult table 동적 출력 함수
function printDailyTable(data) {
  const total = {
    confirmedOrderCount: 0,
    confirmedProductCount: 0,
    sumProductPrice: 0,
    sumDiscountedPrice: 0,
    sumPointUsageAmount: 0,
    sumPayment: 0,
    canceledOrderCount: 0,
    canceledProductCount: 0,
    sumRefundAmount: 0,
    totalNetSales: 0,
  };

  if (data.length > 0) {
    let output = '';
    data.forEach(function (item, index) {
      let completeDate = new Date(item.complete_date).toLocaleDateString();

      let confirmedDate = item.confirmed_date != null ? new Date(item.confirmed_date).toLocaleDateString() : '-';
      let confirmedOrderCount = item.confirmed_date != null ? item.confirmed_order_count : '-';
      let confirmedProductCount = item.confirmed_date != null ? item.confirmed_product_count : '-';
      let sumProductPrice = item.confirmed_date != null ? item.sum_product_price.toLocaleString() : '-';
      let sumDiscountedPrice = item.confirmed_date != null ? item.sum_discounted_price.toLocaleString() : '-';
      let sumPointUsageAmount = item.confirmed_date != null ? item.sum_point_usage_amount.toLocaleString() : '-';
      let sumPayment = item.confirmed_date != null ? item.sum_payment.toLocaleString() : '-';

      let canceledDate = item.canceled_date != null ? new Date(item.canceled_date).toLocaleDateString() : '-';
      let canceledOrderCount = item.canceled_date != null ? item.canceled_order_count : '-';
      let canceledProductCount = item.canceled_date != null ? item.canceled_product_count : '-';
      let sumRefundAmount = item.canceled_date != null ? item.sum_refund_amount.toLocaleString() : '-';

      output += '<tr>';
      output += `<td>${completeDate}</td>`;
      output += `<td>${confirmedDate}</td>`;
      output += `<td>${confirmedOrderCount}</td>`;
      output += `<td>${confirmedProductCount}</td>`;
      output += `<td>${sumProductPrice}</td>`;
      output += `<td>${sumDiscountedPrice}</td>`;
      output += `<td>${sumPointUsageAmount}</td>`;
      output += `<td>${sumPayment}</td>`;
      output += `<td>${canceledDate}</td>`;
      output += `<td>${canceledOrderCount}</td>`;
      output += `<td>${canceledProductCount}</td>`;
      output += `<td>${sumRefundAmount}</td>`;
      output += `<td>${item.total_net_sales.toLocaleString()}</td>`;
      output += '</tr>';

      if (item.confirmed_date != null) {
        total.confirmedOrderCount += item.confirmed_order_count;
        total.confirmedProductCount += item.confirmed_product_count;
        total.sumProductPrice += item.sum_product_price;
        total.sumDiscountedPrice += item.sum_discounted_price;
        total.sumPointUsageAmount += item.sum_point_usage_amount;
        total.sumPayment += item.sum_payment;
      }
      if (item.canceled_date != null) {
        total.canceledOrderCount += item.canceled_order_count;
        total.canceledProductCount += item.canceled_product_count;
        total.sumRefundAmount += item.sum_refund_amount;
      }
      total.totalNetSales += item.total_net_sales;
    });

    output += '<tr>';
    output += `<td>합계</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.confirmedOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.confirmedProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumProductPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumDiscountedPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumPointUsageAmount.toLocaleString()}</td>`;
    output += `<td>${total.sumPayment.toLocaleString()}</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.canceledOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.canceledProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumRefundAmount.toLocaleString()}</td>`;
    output += `<td>${total.totalNetSales.toLocaleString()}</td>`;
    output += '</tr>';

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.');
  }
}

// [주별매출] searchResult table 동적 출력 함수
function printWeeklyTable(data, currentWeekOfYear) {
  const total = {
    confirmedOrderCount: 0,
    confirmedProductCount: 0,
    sumProductPrice: 0,
    sumDiscountedPrice: 0,
    sumPointUsageAmount: 0,
    sumPayment: 0,
    canceledOrderCount: 0,
    canceledProductCount: 0,
    sumRefundAmount: 0,
    totalNetSales: 0,
  };

  if (data.length > 0) {
    let output = '';
    data.forEach(function (item, index) {
      let completeWeek = currentWeekOfYear - item.complete_week + '주 전';

      let confirmedDate =
        item.confirmed_week != null
          ? new Date(item.confirmed_start_date).toLocaleDateString() +
            '<br>~ ' +
            new Date(item.confirmed_end_date).toLocaleDateString()
          : '-';
      let confirmedOrderCount = item.confirmed_week != null ? item.confirmed_order_count : '-';
      let confirmedProductCount = item.confirmed_week != null ? item.confirmed_product_count : '-';
      let sumProductPrice = item.confirmed_week != null ? item.sum_product_price.toLocaleString() : '-';
      let sumDiscountedPrice = item.confirmed_week != null ? item.sum_discounted_price.toLocaleString() : '-';
      let sumPointUsageAmount = item.confirmed_week != null ? item.sum_point_usage_amount.toLocaleString() : '-';
      let sumPayment = item.confirmed_week != null ? item.sum_payment.toLocaleString() : '-';

      let canceledDate =
        item.canceled_week != null
          ? new Date(item.canceled_start_date).toLocaleDateString() +
            '<br>~ ' +
            new Date(item.canceled_end_date).toLocaleDateString()
          : '-';
      let canceledOrderCount = item.canceled_week != null ? item.canceled_order_count : '-';
      let canceledProductCount = item.canceled_week != null ? item.canceled_product_count : '-';
      let sumRefundAmount = item.canceled_week != null ? item.sum_refund_amount.toLocaleString() : '-';

      output += '<tr>';
      output += `<td>${completeWeek}</td>`;
      output += `<td>${confirmedDate}</td>`;
      output += `<td>${confirmedOrderCount}</td>`;
      output += `<td>${confirmedProductCount}</td>`;
      output += `<td>${sumProductPrice}</td>`;
      output += `<td>${sumDiscountedPrice}</td>`;
      output += `<td>${sumPointUsageAmount}</td>`;
      output += `<td>${sumPayment}</td>`;
      output += `<td>${canceledDate}</td>`;
      output += `<td>${canceledOrderCount}</td>`;
      output += `<td>${canceledProductCount}</td>`;
      output += `<td>${sumRefundAmount}</td>`;
      output += `<td>${item.total_net_sales.toLocaleString()}</td>`;
      output += '</tr>';

      if (item.confirmed_week != null) {
        total.confirmedOrderCount += item.confirmed_order_count;
        total.confirmedProductCount += item.confirmed_product_count;
        total.sumProductPrice += item.sum_product_price;
        total.sumDiscountedPrice += item.sum_discounted_price;
        total.sumPointUsageAmount += item.sum_point_usage_amount;
        total.sumPayment += item.sum_payment;
      }
      if (item.canceled_week != null) {
        total.canceledOrderCount += item.canceled_order_count;
        total.canceledProductCount += item.canceled_product_count;
        total.sumRefundAmount += item.sum_refund_amount;
      }
      total.totalNetSales += item.total_net_sales;
    });

    output += '<tr>';
    output += `<td>합계</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.confirmedOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.confirmedProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumProductPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumDiscountedPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumPointUsageAmount.toLocaleString()}</td>`;
    output += `<td>${total.sumPayment.toLocaleString()}</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.canceledOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.canceledProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumRefundAmount.toLocaleString()}</td>`;
    output += `<td>${total.totalNetSales.toLocaleString()}</td>`;
    output += '</tr>';

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.');
  }
}

// [월별매출] searchResult table 동적 출력 함수
function printMonthlyTable(data) {
  const total = {
    confirmedOrderCount: 0,
    confirmedProductCount: 0,
    sumProductPrice: 0,
    sumDiscountedPrice: 0,
    sumPointUsageAmount: 0,
    sumPayment: 0,
    canceledOrderCount: 0,
    canceledProductCount: 0,
    sumRefundAmount: 0,
    totalNetSales: 0,
  };

  if (data.length > 0) {
    let output = '';
    data.forEach(function (item, index) {
      let completeDate = item.complete_date;

      let confirmedDate = item.confirmed_date != null ? item.confirmed_date : '-';
      let confirmedOrderCount = item.confirmed_date != null ? item.confirmed_order_count : '-';
      let confirmedProductCount = item.confirmed_date != null ? item.confirmed_product_count : '-';
      let sumProductPrice = item.confirmed_date != null ? item.sum_product_price.toLocaleString() : '-';
      let sumDiscountedPrice = item.confirmed_date != null ? item.sum_discounted_price.toLocaleString() : '-';
      let sumPointUsageAmount = item.confirmed_date != null ? item.sum_point_usage_amount.toLocaleString() : '-';
      let sumPayment = item.confirmed_date != null ? item.sum_payment.toLocaleString() : '-';

      let canceledDate = item.canceled_date != null ? item.canceled_date : '-';
      let canceledOrderCount = item.canceled_date != null ? item.canceled_order_count : '-';
      let canceledProductCount = item.canceled_date != null ? item.canceled_product_count : '-';
      let sumRefundAmount = item.canceled_date != null ? item.sum_refund_amount.toLocaleString() : '-';

      output += '<tr>';
      output += `<td>${completeDate}</td>`;
      output += `<td>${confirmedDate}</td>`;
      output += `<td>${confirmedOrderCount}</td>`;
      output += `<td>${confirmedProductCount}</td>`;
      output += `<td>${sumProductPrice}</td>`;
      output += `<td>${sumDiscountedPrice}</td>`;
      output += `<td>${sumPointUsageAmount}</td>`;
      output += `<td>${sumPayment}</td>`;
      output += `<td>${canceledDate}</td>`;
      output += `<td>${canceledOrderCount}</td>`;
      output += `<td>${canceledProductCount}</td>`;
      output += `<td>${sumRefundAmount}</td>`;
      output += `<td>${item.total_net_sales.toLocaleString()}</td>`;
      output += '</tr>';

      if (item.confirmed_date != null) {
        total.confirmedOrderCount += item.confirmed_order_count;
        total.confirmedProductCount += item.confirmed_product_count;
        total.sumProductPrice += item.sum_product_price;
        total.sumDiscountedPrice += item.sum_discounted_price;
        total.sumPointUsageAmount += item.sum_point_usage_amount;
        total.sumPayment += item.sum_payment;
      }
      if (item.canceled_date != null) {
        total.canceledOrderCount += item.canceled_order_count;
        total.canceledProductCount += item.canceled_product_count;
        total.sumRefundAmount += item.sum_refund_amount;
      }
      total.totalNetSales += item.total_net_sales;
    });

    output += '<tr>';
    output += `<td>합계</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.confirmedOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.confirmedProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumProductPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumDiscountedPrice.toLocaleString()}</td>`;
    output += `<td>${total.sumPointUsageAmount.toLocaleString()}</td>`;
    output += `<td>${total.sumPayment.toLocaleString()}</td>`;
    output += `<td>-</td>`;
    output += `<td>${total.canceledOrderCount.toLocaleString()}</td>`;
    output += `<td>${total.canceledProductCount.toLocaleString()}</td>`;
    output += `<td>${total.sumRefundAmount.toLocaleString()}</td>`;
    output += `<td>${total.totalNetSales.toLocaleString()}</td>`;
    output += '</tr>';

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.');
  }
}

// searchResult table에 매개 변수로 넘겨받은 메시지로 병합된 하나의 빈 컬럼을 출력하여줌
function printEmptyTable(msg) {
  const value = msg != undefined ? msg : '조건을 설정하고 검색을 진행하여 목록을 받아보실 수 있습니다.';
  let output = `<tr class="empty-row"><td colspan="14">${value}</td></tr>`;
  $('#searchResult').html(output);
}
