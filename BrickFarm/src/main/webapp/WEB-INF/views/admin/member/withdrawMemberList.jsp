<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt"%>
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
      .title {
        display: flex;
        justify-content: space-between;
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
                <h4>탈퇴 회원 관리</h4>
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
                  <a href="${contextPath }/admin/member/memberList"
                    >회원 조회</a
                  >
                </li>
              </ol>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">탈퇴 회원 조회</h4>
                </div>
                <jsp:include page="./memberSearchBar.jsp"></jsp:include>
              </div>
            </div>
            <div class="col-lg-12">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive table-hover">
                    <table
                      id="selectTable"
                      class="display"
                      style="min-width: 845px"
                    >
                      <thead>
                        <tr>
                          <th>이름</th>
                          <th>아이디</th>
                          <th>이메일</th>
                          <th>전화번호</th>
                          <th>탈퇴 일시</th>
                          <th>탈퇴 구분</th>
                          <th>탈퇴 사유</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="member" items="${withdrawMember }">
                          <fmt:formatDate
                            var="withdraw_date"
                            value="${member.withdraw_date}"
                            pattern="yyyy-MM-dd"
                          />
                          <tr
                            data-toggle="modal"
                            data-target="#orderList"
                            onclick="showModalList('${member.member_no}', '1')"
                          >
                            <td>${member.member_name }</td>
                            <td>${member.member_id }</td>
                            <td>${member.email }</td>
                            <td>${member.phone_number }</td>
                            <td>${withdraw_date}</td>
                            <td>${member.withdraw_reason}</td>
                            <td>${member.reason_memo}</td>
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

    <!-- Modal -->
    <div class="modal fade" id="orderList">
      <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원 정보 조회</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="title">
              <h4>회원 정보</h4>
              <a
                data-toggle="modal"
                data-target="#loginLog"
                class="text-align-center"
                href="javascript:void(0)"
                >로그인 정보 조회</a
              >
            </div>
            <hr />
            <div>
              <table class="table table-bordered">
                <tr>
                  <td>회원번호</td>
                  <td id="member_no"></td>
                  <td>등급</td>
                  <td id="member_grade_name"></td>
                </tr>
                <tr>
                  <td>아이디</td>
                  <td class="member_id"></td>
                  <td>가입일</td>
                  <td id="member_regist_date"></td>
                </tr>
                <tr>
                  <td>이름</td>
                  <td id="member_name"></td>
                  <td>생년월일</td>
                  <td id="birth_date"></td>
                </tr>
                <tr>
                  <td>전화번호</td>
                  <td id="phone_number"></td>
                  <td>적립금</td>
                  <td id="accrual_amount"></td>
                </tr>
                <tr>
                  <td>주소</td>
                  <td id="address"></td>
                  <td>이메일</td>
                  <td id="email"></td>
                </tr>
              </table>
            </div>

            <h4>주문 내역</h4>
            <hr />
            <div class="table-responsive table-hover">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>이름</th>
                    <th>아이디</th>
                    <th>등급</th>
                    <th>주문 번호</th>
                    <th>주문 시간</th>
                    <th>주문 상태</th>
                  </tr>
                </thead>
                <tbody class="memberOrderList"></tbody>
              </table>
              <ul
                class="pagination pagination-gutter justify-content-center pagination-sm"
                id="pagination"
              ></ul>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-danger light"
              data-dismiss="modal"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="orderDetailList">
      <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">상세 주문 조회</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div id="accordion-one" class="accordion accordion-primary">
              <div class="accordion__item">
                <div
                  class="accordion__header collapsed rounded-lg"
                  data-toggle="collapse"
                  data-target="#default_collapseTwo"
                >
                  <span class="accordion__header--text">상품 정보</span>
                  <span class="accordion__header--indicator"></span>
                </div>
                <div
                  id="default_collapseTwo"
                  class="collapse accordion__body"
                  data-parent="#accordion-one"
                >
                  <div class="accordion__body--text">
                    <div>
                      <table class="table table-responsive-md">
                        <thead>
                          <tr>
                            <th>#</th>
                            <th>상품명</th>
                            <th>수량</th>
                            <th>판매가</th>
                            <th>실 구매액</th>
                            <th>주문 상태</th>
                            <th>확정 일자</th>
                          </tr>
                        </thead>
                        <tbody id="detailOrderProducts"></tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <div class="accordion__item">
                <div
                  class="accordion__header collapsed rounded-lg"
                  data-toggle="collapse"
                  data-target="#default_collapseThree"
                >
                  <span class="accordion__header--text">배송 정보</span>
                  <span class="accordion__header--indicator"></span>
                </div>
                <div
                  id="default_collapseThree"
                  class="collapse accordion__body"
                  data-parent="#accordion-one"
                >
                  <div class="accordion__body--text">
                    <table class="table table-bordered">
                      <tr>
                        <td>운송장번호</td>
                        <td id="post_no"></td>
                        <td>배송상태</td>
                        <td id="delivery_state"></td>
                      </tr>
                      <tr>
                        <td colspan="2">주문자 정보</td>
                        <td colspan="2">수령자 정보</td>
                      </tr>
                      <tr>
                        <td>이름</td>
                        <td id="buyer_name"></td>
                        <td>이름</td>
                        <td id="recipient"></td>
                      </tr>
                      <tr>
                        <td>아이디</td>
                        <td id="buyer_id"></td>
                        <td>전화번호</td>
                        <td id="recipient_phone"></td>
                      </tr>
                      <tr>
                        <td>전화번호</td>
                        <td id="buyer_phone_number"></td>
                        <td>주소</td>
                        <td id="recipient_address"></td>
                      </tr>
                    </table>
                  </div>
                </div>
              </div>
              <div class="accordion__item">
                <div
                  class="accordion__header collapsed rounded-lg"
                  data-toggle="collapse"
                  data-target="#default_collapseFour"
                >
                  <span class="accordion__header--text">결제 정보</span>
                  <span class="accordion__header--indicator"></span>
                </div>
                <div
                  id="default_collapseFour"
                  class="collapse accordion__body"
                  data-parent="#accordion-one"
                >
                  <div class="accordion__body--text">
                    <div>
                      <h5>결제 정보</h5>
                      <table class="table table-bordered">
                        <tr>
                          <td>총 판매가</td>
                          <td id="total_product_price"></td>
                        </tr>
                        <tr>
                          <td>쿠폰 적용 할인가</td>
                          <td id="total_discounted_price"></td>
                        </tr>
                        <tr>
                          <td>포인트</td>
                          <td id="point_pay_money"></td>
                        </tr>
                        <tr>
                          <td>배송비</td>
                          <td id="post_money"></td>
                        </tr>
                        <tr>
                          <td>취소금액</td>
                          <td id="cancel_money"></td>
                        </tr>
                        <tr>
                          <td>실 결제 금액</td>
                          <td id="total_pay_money"></td>
                        </tr>
                      </table>
                      <h5>결제 수단</h5>
                      <table class="table table-bordered">
                        <tr>
                          <td>결제 수단</td>
                          <td id="payType"></td>
                        </tr>
                        <tr>
                          <td>결제(입금)자</td>
                          <td id="buyer"></td>
                        </tr>
                        <tr>
                          <td>카드사(은행명)</td>
                          <td id="brand"></td>
                        </tr>
                        <tr>
                          <td>카드번호 (가상계좌번호)</td>
                          <td id="checked_info"></td>
                        </tr>
                        <tr>
                          <td>결제 시간</td>
                          <td id="checked_time"></td>
                        </tr>
                        <tr>
                          <td>마감기한</td>
                          <td id="deposit_deadline"></td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-danger light"
              data-dismiss="modal"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="loginLog">
      <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">로그인 정보 조회</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table table-bordered">
                <thead>
                  <tr>
                    <th>로그인 일시</th>
                  </tr>
                </thead>
                <tbody class="loginTable"></tbody>
              </table>
              <ul
                class="pagination pagination-gutter justify-content-center pagination-sm"
                id="loginPagination"
              ></ul>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-danger light"
              data-dismiss="modal"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- Datatable -->
    <script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>
    <script>
      let lang_kor = {
        decimal: "",
        emptyTable: "데이터가 없습니다.",
        info: "_START_ - _END_ (총 _TOTAL_ 명)",
        infoEmpty: "0명",
        infoFiltered: "(전체 _MAX_ 명 중 검색결과)",
        infoPostFix: "",
        thousands: ",",
        lengthMenu: "_MENU_ 명씩 보기",
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

        $("#checkAll").change(function () {
          $('input[name="member"]').prop(
            "checked",
            $("#checkAll").prop("checked")
          );
        });
      });

      function showModalList(member_no, pageNo) {
        showOrderList(member_no, pageNo);
        findMemberDetail(member_no, pageNo);
      }

      function findMemberDetail(member_no, pageNo) {
        let member = {
          member_no: member_no,
          pageNo: 1,
        };

        if (pageNo != null) {
          member = {
            member_no: member_no,
            pageNo: pageNo,
          };
        }

        $.ajax({
          url: "./withdrawMember",
          type: "get",
          data: member,
          async: false,
          success: function (data) {
            parsingMemberInfo(data);
          },
        });
      }
      function parsingMemberInfo(data) {
        let output = "";
        let loginLog = data.memberInfo.loginLog;
        let page = data.pagingInfo;
        let pagination = "";

        // 멤버 정보 기입
        $("#member_no").html(data.memberInfo.member[0].member_no);
        $("#member_grade_name").html(
          data.memberInfo.member[0].member_grade_name
        );
        $("#member_id").html(data.memberInfo.member[0].member_id);
        $("#member_name").html(data.memberInfo.member[0].member_name);
        $("#phone_number").html(data.memberInfo.member[0].phone_number);
        $("#email").html(data.memberInfo.member[0].email);
        $("#birth_date").html(
          data.memberInfo.member[0].birth_date +
            "( " +
            data.memberInfo.member[0].gender +
            " )"
        );
        $("#address").html(data.memberInfo.member[0].address);
        $("#member_regist_date").html(
          new Date(
            data.memberInfo.member[0].member_regist_date
          ).toLocaleString()
        );
        $("#accrual_amount").html(
          data.memberInfo.member[0].accrual_amount.toLocaleString()
        );

        // 로그인 이력 기입
        for (i = 0; i < loginLog.length; i++) {
          output += `<tr><td>\${new Date(loginLog[i].login_date).toLocaleString()}</td></tr>`;
        }

        // 로그인 페이지네이션
        if (page.totalPage > 1) {
          if (page.pageNo > 1) {
            pagination += `<li class="page-item page-indicator">
			            <a class="page-link" onclick="findMemberDetail(\${data.memberInfo.member[0].member_no}, 1)">
			                <i class="la la-angle-left"></i></a>
			        </li>`;
          }

          for (
            let i = page.startNumOfCurPageGroup;
            i < page.endNumOfCurPageGroup + 1;
            i++
          ) {
            if (i == page.pageNo) {
              pagination += ` <li class="page-item active"><a class="page-link" onclick="findMemberDetail(\${data.memberInfo.member[0].member_no}, \${i})">\${i}</a></li>`;
            } else {
              pagination += ` <li class="page-item"><a class="page-link" onclick="findMemberDetail(\${data.memberInfo.member[0].member_no}, \${i})">\${i}</a></li>`;
            }
          }

          if (page.pageNo < page.totalPage) {
            pagination += `<li class="page-item page-indicator">
							            <a class="page-link" onclick="findMemberDetail(\${data.memberInfo.member[0].member_no}, \${page.totalPage})">
						                <i class="la la-angle-right"></i></a>
						        </li>`;
          }
        }

        $("#loginPagination").html(pagination);

        $(".loginTable").html(output);
      }

      function showOrderList(member_no, pageNo) {
        let member = {
          member_no: member_no,
          pageNo: pageNo,
          withdraw_member: true,
        };

        $.ajax({
          url: "./showOrderList",
          type: "post",
          data: member,
          dataType: "text",
          async: false,
          success: function (data) {
            parsingOrderList(data);
          },
        });
      }

      function parsingOrderList(data) {
        let output = "";
        let pagination = "";
        let datainfo = JSON.parse(data);
        let order = datainfo.orderList;
        let page = datainfo.pagingInfo;

        for (i = 0; i < order.length; i++) {
          output += `<tr data-toggle="modal" data-target="#orderDetailList" onclick="showDetailOrder('\${order[i].merchant_uid}')">
				<td>\${order[i].member_name}</td>
				<td>\${order[i].member_id }</td>
				<td>\${order[i].member_grade_name }</td>
				<td>\${order[i].merchant_uid}</td>
				<td>\${new Date(order[i].order_day).toLocaleString()}</td>`;

          if (order[i].total_state == "완료") {
            output += `<td><span class="badge light badge-success">\${order[i].total_state}</span></td>`;
          } else {
            output += `<td><span class="badge light badge-secondary">\${order[i].total_state}</span></td>`;
          }

          output += `</tr>`;
        }

        $(".memberOrderList").html(output);

        if (page.totalPage > 1) {
          if (page.pageNo > 1) {
            pagination += `<li class="page-item page-indicator">
			            <a class="page-link" onclick="showOrderList('\${order[0].member_no}', 1)">
			                <i class="la la-angle-left"></i></a>
			        </li>`;
          }

          for (
            let i = page.startNumOfCurPageGroup;
            i < page.endNumOfCurPageGroup + 1;
            i++
          ) {
            if (i == page.pageNo) {
              pagination += ` <li class="page-item active"><a class="page-link" onclick="showOrderList('\${order[0].member_no}', \${i})">\${i}</a></li>`;
            } else {
              pagination += ` <li class="page-item"><a class="page-link" onclick="showOrderList('\${order[0].member_no}', \${i})">\${i}</a></li>`;
            }
          }

          if (page.pageNo < page.totalPage) {
            pagination += `<li class="page-item page-indicator">
							            <a class="page-link" onclick="showOrderList('\${order[0].member_no}', \${page.totalPage})">
						                <i class="la la-angle-right"></i></a>
						        </li>`;
          }
        }

        $("#pagination").html(pagination);
      }

      function showDetailOrder(merchant_uid) {
        let merchant = {
          merchant_uid: merchant_uid,
          withdraw_member: true,
        };

        $.ajax({
          url: "./showDetailOrder",
          type: "post",
          data: merchant,
          dataType: "text",
          async: false,
          success: function (data) {
            parsingDetailOrderInfo(data);
            parsingDetailOrderProduct(data);
          },
        });
      }

      function parsingDetailOrderInfo(data) {
        let order = JSON.parse(data);

        $("#buyer_name").html(order.member_name);
        $("#buyer_id").html(order.member_id);
        $("#buyer_phone_number").html(order.phone_number);
        $("#recipient").html(order.recipient);
        $("#recipient_address").html(
          order.recipient_address + "( " + order.recipient_zip_code + " )"
        );
        $("#recipient_phone").html(order.recipient_phone);
        $("#delivery_state").html(order.delivery_state);
        $("#post_no").html(order.post_no);
        $("#total_product_price").html(
          order.total_product_price.toLocaleString()
        );
        $("#total_discounted_price").html(
          order.total_discounted_price.toLocaleString()
        );
        $("#post_money").html(order.post_money.toLocaleString());
        $("#point_pay_money").html(order.point_pay_money.toLocaleString());
        $("#cancel_money").html(order.cancel_money.toLocaleString());
        $("#total_pay_money").html(order.total_pay_money.toLocaleString());
        $("#payType").html(order.payType);
        $("#buyer").html(order.buyer);
        if (order.checked_time != null) {
          $("#checked_time").html(
            new Date(order.checked_time).toLocaleString()
          );
        } else {
          $("#checked_time").html("");
        }
        if (order.deposit_deadline != null) {
          $("#deposit_deadline").html(
            new Date(order.deposit_deadline).toLocaleString()
          );
        } else {
          $("#deposit_deadline").html("");
        }
        $("#brand").html(order.brand);
        $("#checked_info").html(order.checked_info);
      }

      function parsingDetailOrderProduct(data) {
        let list = JSON.parse(data).productList;

        let output = "";

        for (i = 0; i < list.length; i++) {
          output += `<tr>
                <td>\${i+1}</td>
                <td><div class="d-flex align-items-center"><img src=\${list[i].product_main_image} class="rounded-lg mr-2" width="40" alt=""/> <span class="w-space-no">\${list[i].product_name}</span></div></td>
                <td>\${list[i].quantity.toLocaleString()}</td>
                <td>\${list[i].product_price.toLocaleString()}</td>
                <td>\${list[i].discounted_price.toLocaleString()}</td>`;
          if (
            list[i].state.includes("취소") ||
            list[i].state.includes("반품")
          ) {
            output += `<td><div class="d-flex align-items-center"><i class="fa fa-circle text-danger mr-1">\${list[i].state}</i></div></td>`;
          } else if (list[i].state.includes("교환")) {
            output += `<td><div class="d-flex align-items-center"><i class="fa fa-circle text-primary mr-1">\${list[i].state}</i></div></td>`;
          } else if (list[i].state.includes("구매확정")) {
            output += `<td><div class="d-flex align-items-center"><i class="fa fa-circle text-success mr-1">\${list[i].state}</i></div></td>`;
          } else {
            output += `<td><div class="d-flex align-items-center"><i class="fa fa-circle text-secondary mr-1">\${list[i].state}</i></div></td>`;
          }
          if (list[i].complete_date != null) {
            output += `<td>\${new Date(list[i].complete_date).toLocaleString()}</td>`;
          } else {
            output += `<td></td>`;
          }
          output += ` </tr>`;
        }

        $("#detailOrderProducts").html(output);
      }
    </script>
  </body>
</html>
