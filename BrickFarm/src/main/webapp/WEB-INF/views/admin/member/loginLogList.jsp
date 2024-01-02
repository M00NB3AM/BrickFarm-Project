<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <!-- Datatable -->
    <link
      href="${contextPath }/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
      rel="stylesheet"
    />
    <style type="text/css">
      .radio {
        padding: 20px;
      }
      table.dataTable thead th {
        font-size: 14px !important;
      }

      .searchBar {
        height: fit-content !important;
      }

      .table {
        width: 100%;
        padding: 0px 0px !important;
      }

      .table-hover tbody tr {
        cursor: pointer;
      }
      #indicator {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
    </style>
  </head>
  <body>
    <div id="preloader">
      <img
        id="indicator"
        src="${contextPath}/resources/admin/images/lego.gif"
        alt=""
      />
    </div>
    <div id="main-wrapper">
      <jsp:include page="../header.jsp"></jsp:include>
      <div class="content-body">
        <div class="container-fluid">
          <div class="row page-titles mx-0">
            <div class="col-sm-6 p-md-0">
              <div class="welcome-text">
                <h4>로그인 이력 관리</h4>
              </div>
            </div>
            <div
              class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex"
            >
              <ol class="breadcrumb">
                <li class="breadcrumb-item">
                  <a href="${contextPath }/admin/member/dashboard">회원</a>
                </li>
                <li class="breadcrumb-item active">
                  <a href="${contextPath }/admin/member/manageMember">회원 관리</a>
                </li>
              </ol>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">접속 기록 관리</h4>
                </div>
                <jsp:include page="./manageMemberSearchBar.jsp"></jsp:include>
              </div>
            </div>
            <div class="col-lg-12">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <table
                      id="selectTable"
                      class="display"
                      style="min-width: 845px"
                    >
                      <thead>
                        <tr>
                          <th>로그인 일시</th>
                          <th>이름</th>
                          <th>아이디</th>
                          <th>이메일</th>
                          <th>전화번호</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="member" items="${loginList }">
                        	<fmt:formatDate var="login_date" value="${member.login_date}" pattern="yyyy-MM-dd HH:mm:ss" />
                          <tr>
                            <td>${login_date }</td>
                            <td>${member.member_name }</td>
                            <td>${member.member_id }</td>
                            <td>${member.email }</td>
                            <td>${member.phone_number }</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Datatable -->
    <script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>
    <script type="text/javascript">
      let lang_kor = {
        decimal: "",
        emptyTable: "데이터가 없습니다.",
        info: "_START_ - _END_ (총 _TOTAL_ 개)",
        infoEmpty: "0명",
        infoFiltered: "(전체 _MAX_ 명 중 검색결과)",
        infoPostFix: "",
        thousands: ",",
        lengthMenu: "_MENU_ 개씩 보기",
        loadingRecords: "로딩중...",
        processing: "처리중...",
        search: "검색 : ",
        zeroRecords: "검색된 데이터가 없습니다.",
        paginate: {
          first: "첫 페이지",
          last: "마지막 페이지",
          next: "다음",
          previous: "이전",
        },
        aria: {
          sortAscending: " :  오름차순 정렬",
          sortDescending: " :  내림차순 정렬",
        },
      };

      $(function () {
        $("#selectTable").DataTable({
          language: lang_kor,
        });
      });
    </script>
  </body>
</html>
