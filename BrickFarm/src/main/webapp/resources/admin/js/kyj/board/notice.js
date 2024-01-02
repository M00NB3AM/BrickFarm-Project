// 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  getNoticeBoardList(params);
  $('#noticeDetail').removeClass('pop');
  $('#noticeWrite').removeClass('pop');
  $('#noticeModify').removeClass('pop');
}

// 파라메터 초기화
function initNoticeParams() {
  setParams('searchKey', $('#searchKey').val());
}

// 공지사항 작성 시 값을 서버에 전달하기 전 유효성 검사
function validation() {
  const elementTitle = $('#writeNoticeTitle');
  const elementCategory = $('#writeNoticeCategory');

  if (elementTitle.val().length < 1) {
    toastrWarning('필수 입력 사항', '제목은 필수 입력 사항입니다.');
    elementTitle.focus();
    return false;
  }

  if (elementCategory.val() == -1) {
    toastrWarning('필수 선택 사항', '분류는 필수 선택 사항입니다.');
    elementTitle.focus();
    return false;
  }

  if ($('#summernoteWrite').summernote('isEmpty')) {
    toastrWarning('필수 입력 사항', '내용은 필수 입력 사항입니다.');
    $('#summernoteWrite').summernote('focus');
    return false;
  }

  return true;
}

// 공지사항 수정 시 값을 서버에 전달하기 전 유효성 검사
function validationModify() {
  const elementTitle = $('#modifyNoticeTitle');
  const elementCategory = $('#modifyNoticeCategory');

  if (elementTitle.val().length < 1) {
    toastrWarning('필수 입력 사항', '제목은 필수 입력 사항입니다.');
    elementTitle.focus();
    return false;
  }

  if (elementCategory.val() == -1) {
    toastrWarning('필수 선택 사항', '분류는 필수 선택 사항입니다.');
    elementTitle.focus();
    return false;
  }

  if ($('#summernoteModify').summernote('isEmpty')) {
    toastrWarning('필수 입력 사항', '내용은 필수 입력 사항입니다.');
    $('#summernoteModify').summernote('focus');
    return false;
  }

  return true;
}

// 공지사항 상세 창의 답글 달기 버튼 핸들러
function showWriteForm() {
  $('#noticeDetail').removeClass('pop');
  $('#noticeModify').removeClass('pop');

  if (!$('#noticeWrite').hasClass('pop')) {
    $('#noticeWrite').addClass('pop');
  }
  document.getElementById('noticeWrite').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 공지사항 상세 창의 게시글 수정 버튼 핸들러
function showModifyForm() {
  $('#noticeDetail').removeClass('pop');
  $('#noticeWrite').removeClass('pop');

  let title = $('#noticeTitle').html();
  let lastSpanIndex = title.lastIndexOf('</span>');
  if (lastSpanIndex != -1) {
    title = title.substring(lastSpanIndex + '</span>'.length);
  }

  $('#modifyNoticeTitle').val(title);
  $('#modifyNoticeCategory').val($('#noticeCategoryName').attr('data-category-no'));
  $('#modifyNoticeCategory').next().children().children().children().html($('#noticeCategoryName').text());
  $('#summernoteModify').summernote('code', $('#noticeContent').html());

  if (!$('#noticeModify').hasClass('pop')) {
    $('#noticeModify').addClass('pop');
  }
  document.getElementById('noticeModify').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 공지사항 작성 폼 취소 버튼 핸들러
function closeWriteForm() {
  resetForm();
}

// 공지사항 수정 폼 취소 버튼 핸들러
function closeModifyForm() {
  $('#noticeModify').removeClass('pop');
}

// 공지사항 작성 창 취소 시 실행될 입력폼 초기화 함수
function resetForm() {
  const writeForm = document.getElementById('writeForm');
  writeForm.reset();
  $('#writeNoticeCategory').val(-1);
  $('#writeNoticeCategory').next().children().children().children().html('분류');
  $('#summernoteWrite').summernote('code', '');
  $('#noticeWrite').removeClass('pop');
}

// 모든 체크 박스에 이벤트 리스너 추가
function addEventListenerToAllCheckBox() {
  $('.check').on('click', function (evt) {
    evt.stopPropagation();
  });
}

// 공지사항 작성 폼 작성하기 버튼 핸들러
function writeNoticeHandler() {
  if (validation()) {
    const param = {};
    param.noticeCategoryNo = $('#writeNoticeCategory').val();
    param.title = $('#writeNoticeTitle').val();
    param.content = $('#summernoteWrite').summernote('code');

    addNotice(param);
  }
}

// 공지사항 수정 폼 수정하기 버튼 핸들러
function modifyNoticeHandler() {
  if (validationModify()) {
    const param = {};
    param.noticeBoardNo = $('#modifyNoticeBoardNo').text();
    param.noticeCategoryNo = $('#modifyNoticeCategory').val();
    param.adminId = $('#modifyNoticeWriter').text();
    param.title = $('#modifyNoticeTitle').val();
    param.content = $('#summernoteModify').summernote('code');

    modifyNotice(param);
  }
}

/* === ajax ==================================================================================================================== */
// 검색 버튼 핸들러에서 호출 될 api 요청 함수
function getNoticeBoardList(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/board/notice/data',
    type: 'get',
    data: params,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printResult(data);
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

// 검색 결과에서 글을 클릭할 시 호출될 api 요청 함수
function getNoticeDetail(noticeBoardNo) {
  $.ajax({
    url: '/admin/board/notice/detail',
    type: 'get',
    data: {
      noticeBoardNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printDetail(data);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('로딩 실패', '데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 상단 고정, 해제 버튼 핸들러
function fixHandler(isFixed) {
  const checkedList = [];
  $('.check:checked').each(function (index, item) {
    checkedList.push(item.value);
  });
  // console.log(checkedList, isFixed);

  $.ajax({
    url: '/admin/board/notice/fix',
    type: 'post',
    data: {
      noticeBoardNoList: checkedList,
      isFixed: isFixed ? 'Y' : 'N',
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('변경 완료', '고정 상태를 변경 완료하였습니다.');

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('변경 실패', '고정 상태 변경 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }

      // admin/adminlogin
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 공지사항 작성 api 요청
function addNotice(param) {
  $.ajax({
    url: '/admin/board/notice/write',
    type: 'post',
    data: param,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('작성 완료', '입력한 내용으로 게시글이 작성되었습니다.');
      resetForm();
      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('작성 실패', '게시글을 작성하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 공지사항 삭제 api 요청
function removeNotice() {
  const noticeBoardNo = $('#noticeBoardNo').text();

  $.ajax({
    url: '/admin/board/notice/remove',
    type: 'post',
    data: {
      noticeBoardNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('삭제 완료', '게시글이 삭제되었습니다. 글번호: ' + noticeBoardNo);

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '게시글을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 공지사항 수정 api 요청
function modifyNotice(param) {
  $.ajax({
    url: '/admin/board/notice/modify',
    type: 'post',
    data: param,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('수정 완료', '입력한 내용으로 게시글이 수정되었습니다.');

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('수정 실패', '게시글을 수정하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

/* === print ==================================================================================================================== */
// ajax 요청이 success 시 검색 결과를 그리기 위한 출력 함수를 일괄적으로 실행시키기 위한 함수
function printResult(data) {
  printPagination(data.paginationInfo);

  printSearchResultTable(data);
}

// searchResult table에 매개 변수로 넘겨받은 값을 기준으로 동적 출력
function printSearchResultTable(data) {
  const fixedNoticeBoardList = data.fixedNoticeBoardList;
  const noticeBoardList = data.noticeBoardList;

  let outputFixed = '';
  let output = '';

  if (fixedNoticeBoardList.length > 0) {
    fixedNoticeBoardList.forEach(function (item, index) {
      let createdDate = new Date(item.created_date).toLocaleDateString();
      // let updatedDate = item.updated_date != null ? new Date(item.updated_date).toLocaleDateString() : "-";

      let status = `<span class="badge light badge-success"><i class="fa fa-circle text-success mr-1"></i>고정</span>`;

      output += `<tr onclick="getNoticeDetail(${item.notice_board_no});">`;
      output += `<td><input type="checkbox" class="check" value="${item.notice_board_no}" /></th>`;
      output += `<td>${item.notice_board_no}</td>`;
      output += `<td>${item.notice_category_name}</td>`;
      output += `<td class="board-title">${item.title}</td>`;
      output += `<td>${item.admin_id}</td>`;
      output += `<td>${createdDate}</td>`;
      output += `<td>${status}</td>`;
      output += '</tr>';
    });
  }

  if (noticeBoardList.length > 0) {
    noticeBoardList.forEach(function (item, index) {
      let createdDate = new Date(item.created_date).toLocaleDateString();
      // let updatedDate = item.updated_date != null ? new Date(item.updated_date).toLocaleDateString() : "-";

      let status = `<span class="badge light badge-secondary"><i class="fa fa-circle text-secondary mr-1"></i>비고정</span>`;

      output += `<tr onclick="getNoticeDetail(${item.notice_board_no});">`;
      output += `<td><input type="checkbox" class="check" value="${item.notice_board_no}" /></th>`;
      output += `<td>${item.notice_board_no}</td>`;
      output += `<td>${item.notice_category_name}</td>`;
      output += `<td class="board-title">${item.title}</td>`;
      output += `<td>${item.admin_id}</td>`;
      output += `<td>${createdDate}</td>`;
      output += `<td>${status}</td>`;
      output += '</tr>';
    });
  }

  if (outputFixed + output == '') {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.', 7);
  } else {
    $('#searchResult').html(outputFixed + output);
    addEventListenerToAllCheckBox();
  }
}

// ajax 요청이 success 시 상세 게시글을 화면에 출력시키기 위한 함수
function printDetail(data) {
  resetForm();

  const notice = data.notice;

  let createdDate = new Date(notice.created_date).toLocaleDateString();
  let updatedDate = notice.updated_date != null ? new Date(notice.updated_date).toLocaleDateString() : '-';

  let status =
    notice.isFixed == 'Y'
      ? `<span class="badge light badge-success"><i class="fa fa-circle text-success mr-1"></i>고정</span>`
      : `<span class="badge light badge-secondary"><i class="fa fa-circle text-secondary mr-1"></i>비고정</span>`;

  $('#writeNoticeBoardNo').html(notice.notice_board_no);
  $('#writeNoticeCategoryName').html(notice.notice_category_name);
  $('#writeNoticeCategoryNo').val(notice.notice_category_no);

  $('#modifyNoticeBoardNo').html(notice.notice_board_no);
  $('#modifyNoticeCategory').val(notice.notice_category_no);
  $('#modifyNoticeCategory').next().children().children().children().html(notice.notice_category_name);
  $('#modifyNoticeWriter').html(notice.admin_id);
  $('#modifyNoticeTitle').val(notice.title);
  $('#summernoteModify').summernote('code', notice.content);

  $('#noticeBoardNo').html(notice.notice_board_no);
  $('#noticeCategoryName').html(notice.notice_category_name);
  $('#noticeCategoryName').attr('data-category-no', notice.notice_category_no);
  $('#noticeWriter').html(notice.admin_id);
  $('#noticeCreatedDate').html(createdDate);
  $('#noticeUpdatedDate').html(updatedDate);
  $('#noticeStatus').html(status);
  $('#noticeTitle').html(notice.title);
  $('#noticeContent').html(notice.content);

  if (!$('#noticeDetail').hasClass('pop')) {
    $('#noticeDetail').addClass('pop');
  }
  document.getElementById('noticeDetail').scrollIntoView({ behavior: 'smooth', block: 'center' });
}
