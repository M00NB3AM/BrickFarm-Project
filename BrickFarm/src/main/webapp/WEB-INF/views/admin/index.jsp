<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Common Stylesheet -->
    <link href="/resources/admin/css/kyj/common.css" rel="stylesheet" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>

    <!--====== chartJs init functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/util-chartjs.js"></script>

    <script>
      $(function () {
        const totalSalesStatBy7DaysJSONString = `${totalSalesStatBy7Days}`;
        if (totalSalesStatBy7DaysJSONString != "") {
          const totalSalesStatBy7Days = JSON.parse(totalSalesStatBy7DaysJSONString);

          setTotalSalesStatChart(totalSalesStatBy7Days, "totalSalesStatBy7Days", "지난 7일간 매출");

          $("#totalSalesAmountBy7Days").html(totalSalesStatBy7Days.total_sales_amount.toLocaleString());
          $("#totalReturnAmountBy7Days").text(totalSalesStatBy7Days.total_return_amount.toLocaleString());
          $("#totalPostMoneyBy7Days").text(totalSalesStatBy7Days.total_post_money.toLocaleString());
          $("#totalUsageAmountBy7Days").text(totalSalesStatBy7Days.total_usage_amount.toLocaleString());
          $("#totalSalesAmountAvarageBy7Days").text(totalSalesStatBy7Days.total_sales_amount_avarage.toLocaleString());
          $("#totalReturnAmountAvarageBy7Days").text(totalSalesStatBy7Days.total_return_amount_avarage.toLocaleString());
          $("#totalNetSalesBy7Days").text(totalSalesStatBy7Days.total_net_sales.toLocaleString());
        }

        const totalSalesStatBy30DaysJSONString = `${totalSalesStatBy30Days}`;
        if (totalSalesStatBy30DaysJSONString != "") {
          const totalSalesStatBy30Days = JSON.parse(totalSalesStatBy30DaysJSONString);

          setTotalSalesStatChart(totalSalesStatBy30Days, "totalSalesStatBy30Days", "지난 30일간 매출");

          $("#totalSalesAmountBy30Days").text(totalSalesStatBy30Days.total_sales_amount.toLocaleString());
          $("#totalReturnAmountBy30Days").text(totalSalesStatBy30Days.total_return_amount.toLocaleString());
          $("#totalPostMoneyBy30Days").text(totalSalesStatBy30Days.total_post_money.toLocaleString());
          $("#totalUsageAmountBy30Days").text(totalSalesStatBy30Days.total_usage_amount.toLocaleString());
          $("#totalSalesAmountAvarageBy30Days").text(totalSalesStatBy30Days.total_sales_amount_avarage.toLocaleString());
          $("#totalReturnAmountAvarageBy30Days").text(totalSalesStatBy30Days.total_return_amount_avarage.toLocaleString());
          $("#totalNetSalesBy30Days").text(totalSalesStatBy30Days.total_net_sales.toLocaleString());
        }

        const memberAndPointsAccrualInfoBy7DaysJSONString = `${memberAndPointsAccrualInfoBy7Days}`;
        if (memberAndPointsAccrualInfoBy7DaysJSONString != "") {
          const memberAndPointsAccrualInfoBy7Days = JSON.parse(memberAndPointsAccrualInfoBy7DaysJSONString);

          setMultipleLineChart(memberAndPointsAccrualInfoBy7Days, "memberAndPointsAccrualInfoBy7Days", "dates");
          printSearchResultTable(memberAndPointsAccrualInfoBy7Days);
        }
        
        const inquiriesGraphData = `${inquiriesGraphData}`;
        if(inquiriesGraphData != "") {
          multipleBarChart(JSON.parse(inquiriesGraphData), 'memberInquiriesStat', 'created_date', {
            aspectRatio: 3,
            barPercentage: 0.4,
          });
        }

        $(".counter").counterUp({
          delay: 30,
          time: 3000,
        });
      });

      function printSearchResultTable(data) {
        let output = "";

        if (data.length > 0) {
          data.forEach(function (item, index) {
            let date = new Date(item.dates).toLocaleDateString();

            output += `<tr>`;
            output += `<td>\${date}</td>`;
            output += `<td>\${item.regist_count.toLocaleString()}명</td>`;
            output += `<td>\${item.withdraw_count.toLocaleString()}명</td>`;
            output += `<td>\${item.total_accrual_log_amount.toLocaleString()}p</td>`;
            output += `</tr>`;
          });

          $("#searchResult").html(output);
        } else {
          printEmptyTable("조건에 일치하는 검색결과가 없습니다.", 4);
        }
      }
    </script>
  </head>
  <body>
    <div id="main-wrapper">
      <jsp:include page="./header.jsp"></jsp:include>
      <div class="content-body">
        <div class="container-fluid">
          <div class="row">
            <div class="col-xl-3 col-xxl-6 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="fi fi-rr-box-open-full"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardCount}</span>건</h3>
                      <p class="mb-0">총 주문건수</p>
                      <small>(당일)</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-xxl-6 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="fi fi-rr-dollar"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">￦<span class="counter ml-0">${dashboardSales}</span></h3>
                      <p class="mb-0">총 판매금액</p>
                      <small>(당일)</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-xxl-6 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="fi fi-rr-inbox-out"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardDeliveryCount}</span>건</h3>
                      <p class="mb-0">배송 준비중</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-xxl-6 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="fi fi-rr-exclamation"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardStateCount}</span>건</h3>
                      <p class="mb-0">취소/교환/반품</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">지난 7일간 매출</h4>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalSalesAmountBy7Days"></span></h2>
                        <p class="fs-16 mb-0">총 판매액</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalReturnAmountBy7Days"></span></h2>
                        <p class="fs-16 mb-0">총 반환액</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalPostMoneyBy7Days"></span></h2>
                        <p class="fs-16 mb-0">총 배송비</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600"><span class="counter" id="totalUsageAmountBy7Days"></span>p</h2>
                        <p class="fs-16 mb-0">총 포인트 변동합</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalSalesAmountAvarageBy7Days"></span></h2>
                        <p class="fs-16 mb-0">평균 판매액</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalReturnAmountAvarageBy7Days"></span></h2>
                        <p class="fs-16 mb-0">평균 반환액</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalNetSalesBy7Days"></span></h2>
                        <p class="fs-16 mb-0">매출액</p>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-12">
                      <canvas id="totalSalesStatBy7Days"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">지난 30일간 매출</h4>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalSalesAmountBy30Days"></span></h2>
                        <p class="fs-16 mb-0">총 판매액</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalReturnAmountBy30Days"></span></h2>
                        <p class="fs-16 mb-0">총 반환액</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalPostMoneyBy30Days"></span></h2>
                        <p class="fs-16 mb-0">총 배송비</p>
                      </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600"><span class="counter" id="totalUsageAmountBy30Days"></span>p</h2>
                        <p class="fs-16 mb-0">총 포인트 변동합</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalSalesAmountAvarageBy30Days"></span></h2>
                        <p class="fs-16 mb-0">평균 판매액</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalReturnAmountAvarageBy30Days"></span></h2>
                        <p class="fs-16 mb-0">평균 반환액</p>
                      </div>
                    </div>
                    <div class="col-md-4 col-sm-12 mb-3">
                      <div class="border px-3 py-3 rounded-xl">
                        <h2 class="fs-28 font-w600">￦<span class="counter" id="totalNetSalesBy30Days"></span></h2>
                        <p class="fs-16 mb-0">매출액</p>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-12">
                      <canvas id="totalSalesStatBy30Days"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <div>
                    <h4 class="card-title">회원/적립금 현황</h4>
                    <small class="mb-0">지난 7일간의 신규 회원 가입 수, 탈티 회원 수, 적립금 적립 현황을 보여줍니다.</small>
                  </div>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col-lg-6 col-md-12">
                      <canvas id="memberAndPointsAccrualInfoBy7Days"></canvas>
                    </div>
                    <div class="table-responsive col-lg-6 col-md-12">
                      <table class="table table-hover header-border table-responsive-sm table-search-result">
                        <thead id="resultHeader">
                          <tr>
                            <th class="wd-5">날짜</th>
                            <th class="wd-3">가입수</th>
                            <th class="wd-3">탈퇴수</th>
                            <th class="wd-7">적립금 적용 현황</th>
                          </tr>
                        </thead>
                        <tbody id="searchResult"></tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <div>
                    <h4 class="card-title">고객 문의 현황</h4>
                    <small class="mb-0">날짜 별로 응답대기 중인 문의 현황을 보여줍니다.</small>
                  </div>
                </div>
                <div class="card-body">
                  <canvas id="memberInquiriesStat"></canvas>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
