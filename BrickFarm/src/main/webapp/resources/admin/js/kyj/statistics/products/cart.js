// 정렬 SELECT 목록
const LIST_ORDER_BY = [
  {
    name: '등록회원수순',
    value: 'regi_member_count',
  },
  {
    name: '등록수량순',
    value: 'sum_cart_quantity',
  },
];

// 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  getCartStat(params);
}

/* === ajax ==================================================================================================================== */
// 검색 버튼 핸들러에서 호출 될 api 요청 함수
function getCartStat(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/statistics/products/cart/data',
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
// 정렬 기준 select의 options element를 LIST_ORDER_BY 기준으로 동적 출력
function printOrderBySelect() {
  let output = '';

  LIST_ORDER_BY.forEach(function (item) {
    output += `<option value='${item.value}'>${item.name}</option>`;
  });

  $('#orderByColumnName').html(output);
}

// ajax 요청이 sucsess 시 통계 정보를 그리기 위한 출력 함수를 일괄적으로 실행시키기 위한 함수
function printStatistics(data) {
  printPagination(data.paginationInfo);

  printTotalCanceledTable(data.cartStat);
  setDoughnutChart(data.cartMemberCountTop10, 'cartMemberCountTop10', 'product_name', 'member_count');
  setDoughnutChart(data.cartRegiCountTop10, 'cartRegiCountTop10', 'product_name', 'sum_quantity');
}

// searchResult table에 매개 변수로 넘겨받은 값을 기준으로 동적 출력
function printTotalCanceledTable(data) {
  if (data.length > 0) {
    let output = '';

    data.forEach(function (item, index) {
      output += '<tr>';
      output += `<td>${index + 1}</td>`;
      output += `<td>${item.product_code}</td>`;
      output += `<td>${item.product_name}</td>`;
      output += `<td>${item.product_price.toLocaleString()}</td>`;
      output += `<td>${item.sum_cart_quantity.toLocaleString()}</td>`;
      output += `<td>${item.today_payment_count.toLocaleString()}</td>`;
      output += `<td>${item.current_stock_quantity.toLocaleString()}</td>`;
      output += `<td>${item.regi_member_count.toLocaleString()}</td>`;
      output += '</tr>';
    });

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.', 8);
  }
}
