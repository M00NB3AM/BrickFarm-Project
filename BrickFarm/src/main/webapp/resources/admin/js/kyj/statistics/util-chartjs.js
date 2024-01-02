// https://colordesigner.io/gradient-generator
const COLORS = [
  '#4c3ff7',
  '#5c43e6',
  '#6c46d5',
  '#7c4ac4',
  '#8c4db3',
  '#9d51a3',
  '#ad5492',
  '#bd5881',
  '#cd5b70',
  '#dd5f5f',
];

const COLORS_HOVER = [
  '#4c3ff7e6',
  '#5c43e6e6',
  '#6c46d5e6',
  '#7c4ac4e6',
  '#8c4db3e6',
  '#9d51a3e6',
  '#ad5492e6',
  '#bd5881e6',
  '#cd5b70e6',
  '#dd5f5fe6',
];

const COLORS_MEMBER_AND_POINTS_ACCRUAL = {
  DEFAULT: [
    '#2f4cdd',
    '#f72b50',
    '#2781d5',
  ],
  HOVER: [
    '#2f4cdde6',
    '#f72b50e6',
    '#2781d5e6',
  ],
}

const TRANSLATED_LABEL = {
  Monday: '월요일',
  Tuesday: '화요일',
  Wednesday: '수요일',
  Thursday: '목요일',
  Friday: '금요일',
  Saturday: '토요일',
  Sunday: '일요일',

  0: '00:00 ~ 01:00',
  1: '01:00 ~ 02:00',
  2: '02:00 ~ 03:00',
  3: '03:00 ~ 04:00',
  4: '04:00 ~ 05:00',
  5: '05:00 ~ 06:00',
  6: '06:00 ~ 07:00',
  7: '07:00 ~ 08:00',
  8: '08:00 ~ 09:00',
  9: '09:00 ~ 10:00',
  10: '10:00 ~ 11:00',
  11: '11:00 ~ 12:00',
  12: '12:00 ~ 13:00',
  13: '13:00 ~ 14:00',
  14: '14:00 ~ 15:00',
  15: '15:00 ~ 16:00',
  16: '16:00 ~ 17:00',
  17: '17:00 ~ 18:00',
  18: '18:00 ~ 19:00',
  19: '19:00 ~ 20:00',
  20: '20:00 ~ 21:00',
  21: '21:00 ~ 22:00',
  22: '22:00 ~ 23:00',
  23: '23:00 ~ 24:00',

  골드: '골드',
  실버: '실버',
  일반: '일반',

  login_count: '로그인 횟수',
  regist_count: '신규가입자',
  confirmed_product_quantity: '구매 상품 수량',
  total_confirmed_price: '구매 상품 금액(만원)',
  canceled_product_quantity: '취소/반품 상품 수량',
  total_canceled_price: '취소/반품 상품 금액(만원)',
  member_count: '회원수',
  total_decided_order_count: '총확정주문수',
  delivery_count: '배송횟수',
  total_accrual_count: '지급 건수',
  total_accrual_amount: '지급 금액(천원)',
  total_usage_count: '사용 건수',
  total_usage_amount: '사용 금액(천원)',
  balance: '잔액(천원)',

  /* 게시판 관리 */
  inquiry_count: '전체 문의 수',
  unanswered_inquiry_count: '응답대기 문의 수',
};

const INDEX_TRANSLATED_LABEL = {
  total_sales_amount: '총판매액',
  total_return_amount: '총반환액',
  total_post_money: '총배송비',
  total_usage_amount: '총포인트',
  total_sales_amount_avarage: '평균 판매액',
  total_return_amount_avarage: '평균 반환액',
  total_net_sales: '매출액',
  
  regist_count: '가입 수',
  withdraw_count: '탈퇴 수',
  total_accrual_log_amount: '적립금 적용 현황',
}

const charts = {};

function setBarChart(data, elementId, labelColumn, valueColumn) {
  // console.log(elementId, data);

  const barChart = document.getElementById(elementId).getContext('2d');

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  charts[elementId] = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels:
        data.length > 0
          ? data.map(function (item) {
              return item[labelColumn];
            })
          : ['조건에 맞는 데이터가 없습니다.'],
      datasets: [
        {
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumn];
                })
              : [1],
          borderWidth: 3,
          borderColor: 'rgba(255,255,255,1)',
          backgroundColor: COLORS.slice(0, data.length),
          hoverBackgroundColor: COLORS_HOVER.slice(0, data.length),
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

function setDoughnutChart(data, elementId, labelColumn, valueColumn) {
  console.log(elementId, data);

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  if (data == undefined) {
    return;
  }

  const doughnutChart = document.getElementById(elementId).getContext('2d');

  charts[elementId] = new Chart(doughnutChart, {
    type: 'doughnut',
    data: {
      weight: 5,
      defaultFontFamily: 'Poppins',
      datasets: [
        {
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumn];
                })
              : [1],
          borderWidth: 3,
          borderColor: 'rgba(255,255,255,1)',
          backgroundColor: data.length > 0 ? COLORS.slice(0, data.length) : '#c8c8c8e6',
          hoverBackgroundColor: data.length > 0 ? COLORS_HOVER.slice(0, data.length) : '#c8c8c8',
        },
      ],
      labels:
        data.length > 0
          ? data.map(function (item) {
              return item[labelColumn];
            })
          : ['조건에 맞는 데이터가 없습니다.'],
    },
    options: {
      weight: 1,
      cutoutPercentage: 70,
      responsive: true,
      maintainAspectRatio: false,
      size: {
        width: 500,
      },
    },
  });
}

function setDoubleDoughnutChart(data, elementId, labelColumn, valueColumnArr) {
  console.log(elementId, data);
  const doughnutChart = document.getElementById(elementId).getContext('2d');

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  charts[elementId] = new Chart(doughnutChart, {
    type: 'doughnut',
    data: {
      weight: 5,
      defaultFontFamily: 'Poppins',
      datasets: [
        {
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumnArr[0]];
                })
              : [1],
          borderWidth: 3,
          borderColor: 'rgba(255,255,255,1)',
          backgroundColor: COLORS.slice(0, data.length),
          hoverBackgroundColor: COLORS_HOVER.slice(0, data.length),
        },
        {
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumnArr[1]];
                })
              : [1],
          borderWidth: 3,
          borderColor: 'rgba(255,255,255,1)',
          backgroundColor: COLORS.slice(0, data.length),
          hoverBackgroundColor: COLORS_HOVER.slice(0, data.length),
        },
      ],
      labels:
        data.length > 0
          ? data.map(function (item) {
              return item[labelColumn];
            })
          : ['조건에 맞는 데이터가 없습니다.'],
    },
    options: {
      weight: 1,
      cutoutPercentage: 70,
      responsive: true,
      maintainAspectRatio: false,
      size: {
        width: 500,
      },
    },
  });
}

function setDoubleBarChart(data, elementId, labelsColumn, labelColumnArr, valueColumnArr) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 200;

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  charts[elementId] = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels:
        data.length > 0
          ? data.map(function (item) {
              return item[labelsColumn];
            })
          : [0],
      datasets: [
        {
          label: labelColumnArr[0],
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumnArr[0]];
                })
              : [0],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(47, 76, 221, 1)',
        },
        {
          label: labelColumnArr[1],
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item[valueColumnArr[1]];
                })
              : [0],
          borderColor: 'rgba(43, 193, 85, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(43, 193, 85, 1)',
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

function setDailyNetSalesDetailChart(data, elementId) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 500;

  if (charts.hasOwnProperty('netSalesDetailChart')) {
    charts.netSalesDetailChart.destroy();
  }

  charts.netSalesDetailChart = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels:
        data.length > 0
          ? data.map(function (item) {
              return new Date(item.complete_date).toLocaleDateString();
            })
          : [],
      datasets: [
        {
          type: 'bar',
          label: '결제합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_payment;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(47, 76, 221, 1)',
        },
        {
          type: 'bar',
          label: '취소/반품 금액합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_refund_amount;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(221, 76, 47, 1)',
        },
        {
          type: 'line',
          label: '순매출',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.total_net_sales;
                })
              : [],
          borderColor: 'rgba(43, 193, 85, 1)',
          borderWidth: '3',
          backgroundColor: 'rgba(0, 0, 0, 0)',
          tension: 0,
        },
      ],
    },
    options: {
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

function setWeeklyNetSalesDetailChart(data, elementId, currentWeekOfYear) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 500;

  if (charts.hasOwnProperty('netSalesDetailChart')) {
    charts.netSalesDetailChart.destroy();
  }

  charts.netSalesDetailChart = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels:
        data.length > 0
          ? data.map(function (item) {
              return currentWeekOfYear - item.complete_week + '주 전';
            })
          : [],
      datasets: [
        {
          type: 'bar',
          label: '결제합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_payment;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(47, 76, 221, 1)',
        },
        {
          type: 'bar',
          label: '취소/반품 금액합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_refund_amount;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(221, 76, 47, 1)',
        },
        {
          type: 'line',
          label: '순매출',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.total_net_sales;
                })
              : [],
          borderColor: 'rgba(43, 193, 85, 1)',
          borderWidth: '3',
          backgroundColor: 'rgba(0, 0, 0, 0)',
          tension: 0,
        },
      ],
    },
    options: {
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

function setMonthlyNetSalesDetailChart(data, elementId) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 500;

  if (charts.hasOwnProperty('netSalesDetailChart')) {
    charts.netSalesDetailChart.destroy();
  }

  charts.netSalesDetailChart = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels:
        data.length > 0
          ? data.map(function (item) {
              return item.complete_date;
            })
          : [],
      datasets: [
        {
          type: 'bar',
          label: '결제합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_payment;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(47, 76, 221, 1)',
        },
        {
          type: 'bar',
          label: '취소/반품 금액합계',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.sum_refund_amount;
                })
              : [],
          borderColor: 'rgba(47, 76, 221, 1)',
          borderWidth: '0',
          backgroundColor: 'rgba(221, 76, 47, 1)',
        },
        {
          type: 'line',
          label: '순매출',
          data:
            data.length > 0
              ? data.map(function (item) {
                  return item.total_net_sales;
                })
              : [],
          borderColor: 'rgba(43, 193, 85, 1)',
          borderWidth: '3',
          backgroundColor: 'rgba(0, 0, 0, 0)',
          tension: 0,
        },
      ],
    },
    options: {
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

// 고객 분석에서 혼합형태의 그래프를 출력하기 위한 함수
function multipleBarChart(data, elementId, labelsColumn, optionObj) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 200;

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  const keys = Object.keys(data[0]).slice(1);
  const labels = [];
  const dataSets = {};
  data.forEach(function (item) {
    keys.forEach(function (key) {
      if (!dataSets.hasOwnProperty(key)) {
        dataSets[key] = [];
      }

      let value;
      if (key.indexOf('price') != -1) {
        value = (item[key] * 0.0001).toFixed(4);
      } else if (key == 'total_accrual_amount' || key == 'total_usage_amount' || key == 'balance') {
        value = (item[key] * 0.001).toFixed(3);
      } else {
        value = item[key];
      }

      dataSets[key] = [...dataSets[key], value];
    });
    labels.push(item[labelsColumn]);
  });

  charts[elementId] = new Chart(barChart, {
    type: 'bar',
    data: {
      defaultFontFamily: 'Poppins',
      labels: labels.map(function (item) {
        if (TRANSLATED_LABEL.hasOwnProperty(item)) {
          return TRANSLATED_LABEL[item];
        } else if (typeof item == 'number') {
          return new Date(item).toLocaleDateString();
        } else {
          return item;
        }
      }),
      datasets: keys.map(function (item, index) {
        return {
          type: item.indexOf('balance') != -1 ? 'line' : 'bar',
          label: TRANSLATED_LABEL[item],
          data: dataSets[item],
          borderColor: COLORS[index],
          borderWidth: item.indexOf('balance') != -1 ? '3' : '0',
          backgroundColor: item.indexOf('balance') != -1 ? 'rgba(0, 0, 0, 0)' : COLORS[index],
          tension: 0,
        };
      }),
    },
    options: {
      responsiveAnimationDuration: 500,
      aspectRatio: optionObj != undefined ? (optionObj.aspectRatio != undefined ? optionObj.aspectRatio : 2) : 2,
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
            barPercentage:
              optionObj != undefined ? (optionObj.barPercentage != undefined ? optionObj.barPercentage : 0.8) : 0.8,
          },
        ],
      },
    },
  });
}

// index 대시보드 - n일간 매출 분석 그래프 데이터 세팅
function setTotalSalesStatChart(data, elementId, labelsTitle) {
  // console.log(elementId, data);

  const barChart = document.getElementById(elementId).getContext('2d');

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }
  
  const labels = Object.keys(data).slice(0, 2).concat(Object.keys(data).slice(4));

  charts[elementId] = new Chart(barChart, {
    // type: 'bar',
    type: 'horizontalBar',
    data: {
      defaultFontFamily: 'Poppins',
      labels: [labelsTitle],
      datasets: labels.map(function (item, index) {
        return {
          label: INDEX_TRANSLATED_LABEL[item],
          data: [data[item]],
          borderColor: COLORS[index],
          borderWidth: 3,
          backgroundColor: COLORS[index],
        };
      }),
    },
    options: {
      responsiveAnimationDuration: 500,
      aspectRatio: 2,
      tooltips: {
        callbacks: {
	      label: function(tooltipItem, data) {
	        let label = data.datasets[tooltipItem.datasetIndex].label || '';
	
            if (label) {
              label += ': ';
            }
            // label += tooltipItem.yLabel.toLocaleString();
            label += tooltipItem.xLabel.toLocaleString();
            return label;
          }
        }
      },
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
            barPercentage: 0.5,
          },
        ],
      },
    },
  });
}

// index 대시보드 - 7일간 회원/적립금 현황 그래프 데이터 세팅
function setMultipleLineChart(data, elementId, labelsColumn) {
  const barChart = document.getElementById(elementId).getContext('2d');
  barChart.height = 200;

  if (charts.hasOwnProperty(elementId)) {
    charts[elementId].destroy();
  }

  const keys = Object.keys(data[0]).slice(1, 3);
  const labels = [];
  const dataSets = {};
  data.forEach(function (item) {
    keys.forEach(function (key) {
      if (!dataSets.hasOwnProperty(key)) {
        dataSets[key] = [];
      }
      dataSets[key] = [...dataSets[key], item[key]];
    });
    labels.push(item[labelsColumn]);
  });

  charts[elementId] = new Chart(barChart, {
    type: 'line',
    data: {
      defaultFontFamily: 'Poppins',
      labels: labels.map(function (item) {
        if (INDEX_TRANSLATED_LABEL.hasOwnProperty(item)) {
          return INDEX_TRANSLATED_LABEL[item];
        } else if (typeof item == 'number') {
          return new Date(item).toLocaleDateString();
        } else {
          return item;
        }
      }),
      datasets: keys.map(function (item, index) {
        return {
          label: INDEX_TRANSLATED_LABEL[item],
          data: dataSets[item],
          borderColor: COLORS_MEMBER_AND_POINTS_ACCRUAL.DEFAULT[index],
          borderWidth: 3,
          backgroundColor: 'rgba(0, 0, 0, 0)',
          tension: 0,
        };
      }),
    },
    options: {
      responsiveAnimationDuration: 500,
      aspectRatio: 2,
      tooltips: {
        callbacks: {
	      label: function(tooltipItem, data) {
	        let label = data.datasets[tooltipItem.datasetIndex].label || '';
	
            if (label) {
              label += ': ';
            }
            label += tooltipItem.yLabel.toLocaleString();
            return label;
          }
        }
      },
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
            barPercentage: 0.5,
          },
        ],
      },
    },
  });
}