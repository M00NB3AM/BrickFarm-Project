// 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  getFaqBoardList(params);
  $('#faqDetail').removeClass('pop');
  $('#faqWrite').removeClass('pop');
  $('#faqModify').removeClass('pop');
}

// 파라메터 초기화
function initFaqParams() {
  setParams('searchKey', $('#searchKey').val());
}

// 자주묻는 질문 작성 시 값을 서버에 전달하기 전 유효성 검사
function validation() {
  const elementTitle = $('#writeFaqTitle');
  const elementCategory = $('#writeFaqCategory');

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

// 자주묻는 질문 수정 시 값을 서버에 전달하기 전 유효성 검사
function validationModify() {
  const elementTitle = $('#modifyFaqTitle');
  const elementCategory = $('#modifyFaqCategory');

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

// 자주묻는 질문 상세 창의 답글 달기 버튼 핸들러
function showWriteForm() {
  $('#faqDetail').removeClass('pop');
  $('#faqModify').removeClass('pop');

  if (!$('#faqWrite').hasClass('pop')) {
    $('#faqWrite').addClass('pop');
  }
  document.getElementById('faqWrite').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 자주묻는 질문 상세 창의 게시글 수정 버튼 핸들러
function showModifyForm() {
  $('#faqDetail').removeClass('pop');
  $('#faqWrite').removeClass('pop');

  let title = $('#faqTitle').html();
  let lastSpanIndex = title.lastIndexOf('</span>');
  if (lastSpanIndex != -1) {
    title = title.substring(lastSpanIndex + '</span>'.length);
  }

  $('#modifyFaqTitle').val(title);
  $('#modifyFaqCategory').val($('#faqCategoryName').attr('data-category-no'));
  $('#modifyFaqCategory').next().children().children().children().html($('#faqCategoryName').text());
  $('#summernoteModify').summernote('code', $('#faqContent').html());

  if (!$('#faqModify').hasClass('pop')) {
    $('#faqModify').addClass('pop');
  }
  document.getElementById('faqModify').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 자주묻는 질문 작성 폼 취소 버튼 핸들러
function closeWriteForm() {
  resetForm();
}

// 자주묻는 질문 수정 폼 취소 버튼 핸들러
function closeModifyForm() {
  $('#faqModify').removeClass('pop');
}

// 자주묻는 질문 작성 창 취소 시 실행될 입력폼 초기화 함수
function resetForm() {
  const writeForm = document.getElementById('writeForm');
  writeForm.reset();
  $('#writeFaqCategory').val(-1);
  $('#writeFaqCategory').next().children().children().children().html('분류');
  $('#summernoteWrite').summernote('code', '');
  $('#faqWrite').removeClass('pop');
}

// 자주묻는 질문 작성 폼 작성하기 버튼 핸들러
function writeFaqHandler() {
  if (validation()) {
    const param = {};
    param.faqCategoryNo = $('#writeFaqCategory').val();
    param.title = $('#writeFaqTitle').val();
    param.content = $('#summernoteWrite').summernote('code');

    addFaq(param);
  }
}

// 자주묻는 질문 수정 폼 수정하기 버튼 핸들러
function modifyFaqHandler() {
  if (validationModify()) {
    const param = {};
    param.faqBoardNo = $('#modifyFaqBoardNo').text();
    param.faqCategoryNo = $('#modifyFaqCategory').val();
    param.adminId = $('#modifyFaqWriter').text();
    param.title = $('#modifyFaqTitle').val();
    param.content = $('#summernoteModify').summernote('code');

    modifyFaq(param);
  }
}

/* === ajax ==================================================================================================================== */
// 검색 버튼 핸들러에서 호출 될 api 요청 함수
function getFaqBoardList(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/board/faq/data',
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
function getFaqDetail(faqBoardNo) {
  $.ajax({
    url: '/admin/board/faq/detail',
    type: 'get',
    data: {
      faqBoardNo,
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

// 자주묻는 질문 작성 api 요청
function addFaq(param) {
  $.ajax({
    url: '/admin/board/faq/write',
    type: 'post',
    data: param,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('추가 완료', '입력한 내용으로 항목이 추가되었습니다.');
	  resetForm();
      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('추가 실패', '항목을 추가하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 자주묻는 질문 삭제 api 요청
function removeFaq() {
  const faqBoardNo = $('#faqBoardNo').text();

  $.ajax({
    url: '/admin/board/faq/remove',
    type: 'post',
    data: {
      faqBoardNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('삭제 완료', '선택한 항목이 삭제되었습니다.');

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '해당 항목을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 자주묻는 질문 수정 api 요청
function modifyFaq(param) {
  $.ajax({
    url: '/admin/board/faq/modify',
    type: 'post',
    data: param,
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('수정 완료', '입력한 내용으로 항목이 수정되었습니다.');

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('수정 실패', '해당 항목을 수정하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
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
  const faqBoardList = data.faqBoardList;

  let output = '';

  if (faqBoardList.length > 0) {
    faqBoardList.forEach(function (item, index) {
      let createdDate = new Date(item.created_date).toLocaleDateString();
      // let updatedDate = item.updated_date != null ? new Date(item.updated_date).toLocaleDateString() : "-";

      output += `<tr onclick="getFaqDetail(${item.faq_board_no});">`;
      output += `<td>${item.faq_board_no}</td>`;
      output += `<td>${item.faq_category_name}</td>`;
      output += `<td class="board-title">${item.title}</td>`;
      output += `<td>${createdDate}</td>`;
      output += '</tr>';
    });

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.', 4);
  }
}

// ajax 요청이 success 시 상세 게시글을 화면에 출력시키기 위한 함수
function printDetail(data) {
  resetForm();
  $('#faqModify').removeClass('pop');

  const faq = data.faq;

  let createdDate = new Date(faq.created_date).toLocaleDateString();
  let updatedDate = faq.updated_date != null ? new Date(faq.updated_date).toLocaleDateString() : '-';

  $('#modifyFaqBoardNo').html(faq.faq_board_no);
  $('#modifyFaqCategory').val(faq.faq_category_no);
  $('#modifyFaqCategory').next().children().children().children().html(faq.faq_category_name);
  $('#modifyFaqWriter').html(faq.admin_id);
  $('#modifyFaqTitle').val(faq.title);
  $('#summernoteModify').summernote('code', faq.content);

  $('#faqBoardNo').html(faq.faq_board_no);
  $('#faqCategoryName').html(faq.faq_category_name);
  $('#faqCategoryName').attr('data-category-no', faq.faq_category_no);
  $('#faqWriter').html(faq.admin_id);
  $('#faqCreatedDate').html(createdDate);
  $('#faqUpdatedDate').html(updatedDate);
  $('#faqTitle').html(faq.title);
  $('#faqContent').html(faq.content);

  if (!$('#faqDetail').hasClass('pop')) {
    $('#faqDetail').addClass('pop');
  }
  document.getElementById('faqDetail').scrollIntoView({ behavior: 'smooth', block: 'center' });
}
