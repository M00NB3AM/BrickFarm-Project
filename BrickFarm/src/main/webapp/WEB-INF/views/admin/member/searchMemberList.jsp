<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <!-- Datatable -->
    <link href="${contextPath }/resources/admin/vendor/datatables/css/jquery.dataTables.min.css" rel="stylesheet" />
    <!-- Toastr -->
    <link rel="stylesheet" href="/resources/admin/vendor/toastr/css/toastr.min.css" />

    <style type="text/css">
      .radio {
        padding: 20px;
      }

      .coupon {
        padding: 0.55rem 0.8rem !important;
        margin-bottom: 5px;
        font-size: 14px !important;
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

      svg {
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
      <img id="indicator" src="${contextPath}/resources/admin/images/lego.gif" alt="" />
    </div>
    <div id="main-wrapper">
      <jsp:include page="../header.jsp"></jsp:include>
      <div class="content-body">
        <div class="container-fluid">
          <div class="row page-titles mx-0">
            <div class="col-sm-6 p-md-0">
              <div class="welcome-text">
                <h4>회원 조회</h4>
              </div>
            </div>
            <div class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
              <ol class="breadcrumb">
                <li class="breadcrumb-item">
                  <a href="${contextPath }/admin/member/dashboard">회원</a>
                </li>
                <li class="breadcrumb-item active">
                  <a href="${contextPath }/admin/member/memberList">회원 조회</a>
                </li>
              </ol>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-12">
              <div class="card searchBar">
                <div class="card-header">
                  <h4 class="card-title">회원 조회</h4>
                </div>
                <jsp:include page="./memberSearchBar.jsp"></jsp:include>
              </div>
              <div class="col-lg-12 table">
                <div class="card">
                  <div class="card-body">
                    <button
                      type="button"
                      class="btn light btn-primary coupon"
                      data-bs-toggle="modal"
                      data-bs-target="#couponModal"
                      onclick="return giveCoupon()"
                    >
                      쿠폰 지급
                    </button>
                    <div class="table-responsive table-hover">
                      <table id="selectTable" class="display" style="min-width: 845px">
                        <thead>
                          <tr>
                            <th>
                              <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" />
                                <label class="custom-control-label" for="checkAll"></label>
                              </div>
                            </th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>전화번호</th>
                            <th>적립금</th>
                            <th>총 구매 금액</th>
                            <th>가입일</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach var="member" items="${selectedMember }">
                            <fmt:formatNumber var="accrual_amount" value="${member.accrual_amount}" pattern="###,###,###" />
                            <fmt:formatNumber var="sum_purchase_money" value="${member.sum_purchase_money}" pattern="###,###,###" />
                            <fmt:formatDate var="member_regist_date" value="${member.member_regist_date}" pattern="yyyy-MM-dd" />
                            <tr id="${member.member_no }">
                              <td>
                                <div class="custom-control custom-checkbox">
                                  <input
                                    type="checkbox"
                                    class="custom-control-input ${member.member_grade_name }"
                                    id="coupon${member.member_no }"
                                    value="${member.member_no }"
                                    name="member"
                                  />
                                  <label class="custom-control-label" for="coupon${member.member_no }"></label>
                                </div>
                              </td>
                              <td>${member.member_name }</td>
                              <td>${member.email }</td>
                              <td>${member.phone_number }</td>
                              <td>${accrual_amount}</td>
                              <td>${sum_purchase_money}</td>
                              <td>${member_regist_date }</td>
                              <td>
                                <div class="dropdown ml-auto text-right">
                                  <div class="btn-link" data-toggle="dropdown">
                                    <svg width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                      <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                        <rect x="0" y="0" width="24" height="24"></rect>
                                        <circle fill="#000000" cx="5" cy="12" r="2"></circle>
                                        <circle fill="#000000" cx="12" cy="12" r="2"></circle>
                                        <circle fill="#000000" cx="19" cy="12" r="2"></circle>
                                      </g>
                                    </svg>
                                  </div>
                                  <div class="dropdown-menu dropdown-menu-right">
                                    <a
                                      class="dropdown-item"
                                      data-toggle="modal"
                                      data-target="#memberDetail"
                                      onclick="findMemberDetail('${member.member_no}')"
                                    >
                                      상세조회
                                    </a>
                                    <a
                                      class="dropdown-item"
                                      data-toggle="modal"
                                      data-target="#deleteMember"
                                      onclick="delMember('${member.member_id }','${member.member_no }')"
                                    >
                                      탈퇴하기
                                    </a>
                                  </div>
                                </div>
                              </td>
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
    </div>

    <!--회원 탈퇴 Modal -->
    <div class="modal fade" id="deleteMember">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원 탈퇴</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="modalReset('#deleteMember')">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <h6><span class="delMemberNo"></span>번 <span class="delMember"></span>님을 탈퇴시키겠습니까?</h6>
            <div>
              <div>
                탈퇴 사유 :
                <select class="selectDelReason">
                  <option selected>관리자에 의한 강제 탈퇴</option>
                </select>
              </div>
              <div>메모 : <input type="text" name="reason_memo" id="reason_memo" /></div>
            </div>
            <div id="output"></div>
            지금 탈퇴시킨다면 복구가 어렵다는 것을 인지하고 있습니다.
            <input type="checkbox" id="doubleCheck" />
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#deleteMember')">취소</button>
            <button type="button" class="btn light btn-primary" onclick="return deleteMember()">탈퇴하기</button>
          </div>
        </div>
      </div>
    </div>

    <!--쿠폰 지급 Modal -->

    <div class="modal" id="couponModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">쿠폰 지급</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="modalReset('#couponModal')">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div>쿠폰 이름 :<span class="col-sm-9 selectCoupon"></span></div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#couponModal')">취소</button>
            <button type="button" class="btn btn-primary" onclick="return giveCouponToMember()">지급하기</button>
          </div>
        </div>
      </div>
    </div>

    <!--문자 발송 여부 Modal -->

    <div class="modal" id="messagingModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">문자보내기</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="modalReset('#messagingModal')">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div>
              쿠폰 지급이 완료되었습니다. 해당 회원에게 쿠폰 지급 알림 문자를 발송하시겠습니까?
              <input type="hidden" class="sendMember" />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#messagingModal')">아니오</button>
            <button type="button" class="btn btn-primary" onclick="return sendMessageToMember()">문자 발송하기</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 문자 발송 Modal -->
    <div class="modal" id="messagingToMemberModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">문자보내기</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="modalReset('#messagingToMemberModal')">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group row">
              <label class="col-sm-3 col-form-label">회원 번호</label>
              <div class="col-sm-9">
                <input class="form-control sendMember" type="text" name="to" disabled />
              </div>
            </div>
            <div class="form-group row">
              <label class="col-sm-3 col-form-label">문자 내용</label>
              <div class="col-sm-9">
                <input class="form-control" id="sendContent" type="text" name="content" placeholder="문자 내용 작성하기" />
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#messagingToMemberModal')">아니오</button>
            <button type="button" class="btn btn-primary" onclick="return sendMessage()">문자 발송하기</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 회원 상세정보 Modal -->

    <div class="modal" id="memberDetail">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원 상세 정보</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="modalReset('#memberDetail')">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
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
                  <td id="member_id"></td>
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
                  <td>이메일</td>
                  <td id="email"></td>
                  <td>총 실결제 금액</td>
                  <td id="sum_purchase_money"></td>
                </tr>
                <tr>
                  <td>주소</td>
                  <td colspan="3" id="address"></td>
                </tr>
              </table>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#memberDetail')">닫기</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Datatable -->
    <script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>
    <!-- Toastr -->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
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

      function findMemberDetail(member) {
        $.ajax({
          url: "./searchMember",
          type: "get",
          data: {
            member_no: member,
          },
          dataType: "text",
          async: false,
          success: function (data) {
            let memberInfo = JSON.parse(data);

            $("#member_no").html(memberInfo[0].member_no);
            $("#member_grade_name").html(memberInfo[0].member_grade_name);
            $("#member_id").html(memberInfo[0].member_id);
            $("#member_name").html(memberInfo[0].member_name);
            $("#phone_number").html(memberInfo[0].phone_number);
            $("#email").html(memberInfo[0].email);
            $("#birth_date").html(memberInfo[0].birth_date + "( " + memberInfo[0].gender + " )");
            $("#address").html(memberInfo[0].address);
            $("#member_regist_date").html(new Date(memberInfo[0].member_regist_date).toLocaleString());
            $("#accrual_amount").html(memberInfo[0].accrual_amount.toLocaleString());
            $("#sum_purchase_money").html(memberInfo[0].sum_purchase_money.toLocaleString());
          },
        });
      }

      function delMember(id) {
        $(".delMember").html(id);
      }

      function deleteMember() {
        let result = false;
        let deleteMember = {
          member_id: $(".delMember").html(),
          reason_memo: $("#reason_memo").val(),
          withdraw_reason: "관리자에 의한 강제 탈퇴",
        };

        console.log(deleteMember.member_id);
        console.log(deleteMember.reason_memo);
        console.log(deleteMember.withdraw_reason);
        if ($("#doubleCheck").prop("checked") && $("#reason_memo").val() != "") {
          result = true;

          $.ajax({
            url: "./deleteMember",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(deleteMember),
            dataType: "text",
            async: false,
            success: function (data) {
              toastSuccess("탈퇴 처리를 완료했습니다.", "탈퇴 완료");
              location.href = "./memberList";
            },
            error: function (data) {
              console.log(data.responseText);
              console.log("제이슨 에러");
            },
          });
        } else if ($("#reason_memo").val() == "") {
          toastError("탈퇴 사유를 작성해주세요.", "필수 사항 확인");
        } else if (!$("#doubleCheck").prop("checked")) {
          toastError("지금 탈퇴하면 복구가 어렵다는 설명에 체크해주세요.", "중복 확인");
        }

        return result;
      }

      function modalReset(modalName) {
        $("#reason_memo").val("");
        $("#doubleCheck").prop("checked", false);
        $(modalName).hide();
      }

      let result = [];
      function giveCoupon() {
        result = [];
        let coupon = {
          member_grade_name: "all",
        };
        let classresult = [];

        // 선택된 목록 가져오기
        const query = 'input[name="member"]:checked';
        const selectedEls = document.querySelectorAll(query);

        selectedEls.forEach((el) => {
          if (!classresult.includes(el.className.split(" ")[1])) {
            let member_grade_name = el.className.split(" ")[1];
            classresult.push(member_grade_name);
          }
          result.push(el.value);
        });

        if (result.length == 0) {
          toastError("쿠폰 지급 대상을 선택하세요.", "쿠폰 대상 선택");
          return false;
        } else {
          $("#couponModal").show();
        }
        if (classresult.length == 1) {
          coupon = {
            member_grade_name: classresult[0],
          };
        }

        $.ajax({
          url: "./coupon",
          type: "post",
          contentType: "application/json",
          data: JSON.stringify(coupon),
          dataType: "text",
          async: false,
          success: function (data) {
            let output = "<select id='choose_coupon' name='member_grade_name'>";
            output += "<option value='0'>쿠폰을 선택하세요.</option>";
            $.each(JSON.parse(data), function (index, coupon) {
              output += `<option value="\${coupon.coupon_policy_no}" class="\${coupon.member_grade_name}">\${coupon.coupon_policy_name}</option>`;
            });

            output += "</select>";

            $(".selectCoupon").html(output);
            $("#choose_coupon").selectpicker("refresh");
          },
        });
      }

      function giveCouponToMember() {
        let memberDTO = {
          coupon_policy_no: $("#choose_coupon").val(),
          member_no: result,
        };

        if ($("#choose_coupon").val() == "0") {
          toastError("쿠폰을 선택해주세요.", "쿠폰 선택 필수");
          return false;
        }

        $.ajax({
          url: "./giveCouponToMember",
          type: "post",
          contentType: "application/json",
          data: JSON.stringify(memberDTO),
          dataType: "text",
          async: false,
          success: function (data) {
            toastSuccess("쿠폰 지급 완료!", "지급 완료");
            $("#couponModal").hide();
            $("#messagingModal").show();
            $(".sendMember").val(result);
            $('input[type="checkbox"]').prop("checked", false);
          },
        });
      }

      function sendMessageToMember() {
        $("#messagingToMemberModal").show();
        $("#messagingModal").hide();
      }

      function sendMessage() {
        let message = {
          to: $(".sendMember").val(),
          content: $("#sendContent").val(),
        };
        $.ajax({
          url: "./sendMessageToMember",
          type: "post",
          contentType: "application/json",
          data: JSON.stringify(message),
          dataType: "text",
          async: false,
          success: function (data) {
            toastSuccess("문자 발송에 성공했습니다!", "문자 발송");
            $("#messagingToMemberModal").hide();
          },
        });
      }

      $(function () {
        $("#checkAll").change(function () {
          $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
        });

        $("#selectTable").DataTable({
          language: lang_kor,
        });

        $(document).on("click", ".paginate_button", function () {
          $("#checkAll").prop("checked", false);
          $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
        });

        $(document).on("click", ".dropdown", function () {
          $("#checkAll").prop("checked", false);
          $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
        });

        $(document).on("click", "#selectTable_filter", function () {
          $("#checkAll").prop("checked", false);
          $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
        });
      });

      function toastSuccess(message, title) {
        toastr.success(message, title, {
          positionClass: "toast-top-center",
          timeOut: 5e3,
          closeButton: !0,
          debug: !1,
          newestOnTop: !0,
          progressBar: !0,
          preventDuplicates: !0,
          onclick: null,
          showDuration: "300",
          hideDuration: "1000",
          extendedTimeOut: "1000",
          showEasing: "swing",
          hideEasing: "linear",
          showMethod: "fadeIn",
          hideMethod: "fadeOut",
          tapToDismiss: !1,
        });
      }

      function toastError(message, title) {
        toastr.error(message, title, {
          positionClass: "toast-top-center",
          timeOut: 5e3,
          closeButton: !0,
          debug: !1,
          newestOnTop: !0,
          progressBar: !0,
          preventDuplicates: !0,
          onclick: null,
          showDuration: "300",
          hideDuration: "1000",
          extendedTimeOut: "1000",
          showEasing: "swing",
          hideEasing: "linear",
          showMethod: "fadeIn",
          hideMethod: "fadeOut",
          tapToDismiss: !1,
        });
      }
    </script>
  </body>
</html>
