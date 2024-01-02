<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />

    <!-- Common Stylesheet -->
    <link href="/resources/admin/css/kyj/common.css" rel="stylesheet" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== chartJs init functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/util-chartjs.js"></script>
    
    <script>
      $(function() {
        $(".counter").counterUp({
          delay: 30,
          time: 3000,
        });
        
        const inquiriesGraphData = `${inquiriesGraphData}`;
        if(inquiriesGraphData != "") {
      	  // console.log(JSON.parse(inquiriesGraphData));
          multipleBarChart(JSON.parse(inquiriesGraphData), 'memberInquiriesStat', 'created_date', {
            aspectRatio: 3,
            barPercentage: 0.4,
          });
        }
      });
    </script>
  </head>
  <body>
    <div id="preloader">
      <img id="lego" src="/resources/admin/images/lego.gif" alt="" />
    </div>
    <div id="main-wrapper">
      <jsp:include page="/WEB-INF/views/admin/header.jsp"></jsp:include>
      <!--**********************************
            Content body start
        ***********************************-->
      <div class="content-body">
        <!-- row -->
        <div class="container-fluid">
          <div class="form-head d-flex mb-3 align-items-start">
            <div class="mr-auto d-none d-lg-block">
              <h2 class="text-black font-w600 mb-0">대시보드</h2>
              <p class="mb-0">각 게시판들의 현황을 간략하게 확인할 수 있습니다.</p>
            </div>
          </div>
          <div class="row">
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-announcement"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.notice}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">게시글 수</p>
                      <small>공지사항</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-announcement"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.notice_category}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">분류 수</p>
                      <small>공지사항</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-help"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.faq}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">게시글 수</p>
                      <small>자주묻는 질문</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-help"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.faq_category}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">분류 수</p>
                      <small>자주묻는 질문</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-headphone-alt"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.today_unanswered_inquiry}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">오늘의 응답대기 문의 수</p>
                      <small>문의</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-headphone-alt"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.unanswered_inquiry}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">응답대기 문의 수</p>
                      <small>문의</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-headphone-alt"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.inquiry}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">게시글 수</p>
                      <small>문의</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-headphone-alt"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.inquiry_category}" pattern="#,###" />
                        </span>
                        <small>개</small>
                      </h3>
                      <p class="mb-0">분류 수</p>
                      <small>문의</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-xl-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-headphone-alt"></i>
                    </span>
                    <div class="media-body">
                      <h3 class="mb-0 text-black">
                        <span class="counter ml-0">
                          <fmt:formatNumber value="${boardsStat.inquiry_images_file_size}" pattern="#,###" />
                        </span>
                        <small>byte</small>
                      </h3>
                      <p class="mb-0">이미지 파일 용량</p>
                      <small>문의</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">고객 문의 현황</h4>
                </div>
                <div class="card-body">
                  <canvas id="memberInquiriesStat"></canvas>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
      <!--**********************************
            Content body end
        ***********************************-->
    </div>
  </body>
</html>
