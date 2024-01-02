// 각종 함수에서 기간 별로 구분하여 다른 과정으로 처리하고자 할 때 참고할 상수
const PERIOD = {
  daily: {
    id: 'Daily',
    label: ['최근 7일 평균', '어제', '오늘'],
  },
  weekly: {
    id: 'Weekly',
    label: ['최근 4주 평균', '지난주', '이번주'],
  },
  monthly: {
    id: 'Monthly',
    label: ['최근 3개월 평균', '지난달', '이번달'],
  },
};

// 통계 대시보드에서만 사용될 chartjs init 함수
function setBarChartNetSales(data, period) {
  const barChartNetSales = document.getElementById('chartNetSales' + period.id).getContext('2d');
  barChartNetSales.height = 200;

  new Chart(barChartNetSales, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels: [period.label[0], period.label[1], period.label[2]],
      datasets: [
        {
          label: '순매출액',
          data: [data.recent_period_average, data.last_period, data.this_period],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(47, 76, 221, 1)',
        },
      ],
    },
    options: {
      legend: false,
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
            },
          },
        ],
        xAxes: [
          {
            // Change here
            barPercentage: 0.5,
          },
        ],
      },
    },
  });
}

/* === ajax ==================================================================================================================== */
// 대시보드 페이지에 표현할 정보 api 요청 함수
function getDashboardInfo() {
  $.ajax({
    url: '/admin/statistics/dashboard/info',
    type: 'get',
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
// ajax 요청이 success 시 통계 정보를 그리기 위한 출력 함수를 일괄적으로 실행시키기 위한 함수
function printStatistics(data) {
  printTodayTotalNetSales(data.todayTotalNetSales);
  printNetSalesByPeriod(data.dailyNetSales, PERIOD.daily);
  printNetSalesByPeriod(data.weeklyNetSales, PERIOD.weekly);
  printNetSalesByPeriod(data.monthlyNetSales, PERIOD.monthly);

  //setDoughnutChart(data.dailySalesCountRank, "chartDailySalesCountRank", "product_name", "total_confirmed_quantity");
  //setDoubleDoughnutChart(data.dailySalesCountRank, "chartDailySalesCountRank", "product_name", ["total_confirmed_quantity", "total_order_count"]);
  setDoubleBarChart(
    data.dailySalesCountRank,
    'chartDailySalesCountRank',
    'product_name',
    ['판매량', '주문수'],
    ['total_confirmed_quantity', 'total_order_count']
  );

  setDoughnutChart(data.dailyCartRank, 'chartDailyCartRank', 'product_name', 'total_cart_quantity');
  setDoughnutChart(data.weeklyCancelCountRank, 'chartWeeklyCancelCountRank', 'product_name', 'total_cancel_quantity');
  setDoughnutChart(data.weeklyCancelRatioRank, 'chartWeeklyCancelRatioRank', 'product_name', 'cancel_ratio');
  setDoughnutChart(
    data.weeklySalesRankPerCategory,
    'chartWeeklySalesRankPerCategory',
    'product_category_name',
    'total_confirmed_quantity'
  );
  setDoughnutChart(
    data.preMonthSalesRankPerCategory,
    'chartPreMonthSalesRankPerCategory',
    'product_category_name',
    'total_confirmed_quantity'
  );
}

// 오늘의 통계 정보를 화면에 출력
function printTodayTotalNetSales(data) {
  $('#dailyNetSales').html(data.daily_net_sales.toLocaleString());
  $('#dailyRefundAmount').html(data.daily_refund_amount.toLocaleString());
  $('#dailyPaymentCount').html(data.daliy_payment_count.toLocaleString());
  $('#dailyCancelExchangeCount').html(data.daily_cancel_exchange_count.toLocaleString());
  $('#dailyDeliveryWaitingCount').html(data.daily_delivery_waiting_count.toLocaleString());
  $('#dailyDeliveryCompleteCount').html(data.daily_delivery_complete_count.toLocaleString());
}

// 기간별 순매출 카드안의 정보를 출력
function printNetSalesByPeriod(data, period) {
  $('#recentPeriodAverage' + period.id).html(data.recent_period_average.toLocaleString());
  $('#lastPeriod' + period.id).html(data.last_period.toLocaleString());
  $('#thisPeriod' + period.id).html(data.this_period.toLocaleString());

  setBarChartNetSales(data, period);
}
