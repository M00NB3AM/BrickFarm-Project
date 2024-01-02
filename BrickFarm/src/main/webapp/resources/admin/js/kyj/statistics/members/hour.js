// 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  geStatByHour(params);
}

/* === ajax ==================================================================================================================== */
// 검색 버튼 핸들러에서 호출 될 api 요청 함수
function geStatByHour(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/statistics/members/hour/data',
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
// ajax 요청이 sucsess 시 통계 정보를 그리기 위한 출력 함수를 일괄적으로 실행시키기 위한 함수
function printStatistics(data) {
  printStatByHourTable(data.statByHour);

  multipleBarChart(data.statByHour, 'statByHour', 'hour');
}

// searchResult table에 매개 변수로 넘겨받은 값을 기준으로 동적 출력
function printStatByHourTable(data) {
  if (data != undefined && data.length > 0) {
    let output = '';

    data.forEach(function (item, index) {
      output += '<tr>';
      output += `<td>${TRANSLATED_LABEL[item.hour]}</td>`;
      output += `<td>${item.login_count.toLocaleString()}</td>`;
      output += `<td>${item.regist_count.toLocaleString()}</td>`;
      output += `<td>${item.confirmed_product_quantity.toLocaleString()}</td>`;
      output += `<td>${item.total_confirmed_price.toLocaleString()}</td>`;
      output += `<td>${item.canceled_product_quantity.toLocaleString()}</td>`;
      output += `<td>${item.total_canceled_price.toLocaleString()}</td>`;
      output += '</tr>';
    });

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.', 8);
  }
}
