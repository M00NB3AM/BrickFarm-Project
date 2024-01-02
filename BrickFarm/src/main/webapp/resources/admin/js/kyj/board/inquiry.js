// 임시 업로드된 파일들을 관리할 배열
let FILE_LIST = [];

// 현재 열려있는 문의 글의 원본 파일 목록을 저장할 배열
let FILE_LIST_OF_CURRENT_INQUIRY = [];

// 이미지 미리보기 시 선택 이벤트가 발생한 곳의 정보, DETAIL, WRITE, MODIFY
let MODAL_COLLER = '';

// 현재 파라메터를 기준으로 검색결과를 요청
function search() {
  getInquiryBoardList(params);
  $('#inquiryDetail').removeClass('pop');
}

// 파라메터 초기화
function initInquiryParams() {
  setParams('searchKey', $('#searchKey').val());
}

// 문의글 상세보기에서 첨부 이미지 섬네일 클릭 시 크게 보기 모달창 띄우기
function viewDetailImg(imgSrc, imgAlt, modalColler) {
  // console.log(imgSrc, imgAlt, modalColler);
  $('#imgModal').attr('src', imgSrc);
  $('#imgModal').attr('alt', imgAlt);
  $('#modal').modal();
  
  MODAL_COLLER = modalColler;
}

// 이미지 모달 왼쪽 화살표 버튼 핸들러
function handlerPrevImg() {
  // console.log(MODAL_COLLER);
  let wrapperSelector = '';
  switch(MODAL_COLLER) {
  	case 'DETAIL':
  	  wrapperSelector = '#detailAttachment'
  	  break;
  	case 'WRITE':
  	  wrapperSelector = '#uploadFilesAttachment'
  	  break;
  	case 'MODIFY':
  	  wrapperSelector = '#modifyUploadFilesAttachment'
  }
  const images = $(wrapperSelector + '.wrapper-img').children('.img-uploaded-file');
  const currentSrc = $('#imgModal').attr('src');

  let currentIndex;
  for (i = 0; i < images.length; i++) {
    // console.log($(images[i]).attr("data-detail-src"));
    // console.log(currentSrc);
    if ($(images[i]).attr('data-detail-src') == currentSrc) {
      currentIndex = i;
      break;
    }
  }

  // console.log(currentIndex)
  if (currentIndex > 0) {
    $('#imgModal').attr('src', $(images[currentIndex - 1]).attr('data-detail-src'));
  } else {
    $('#imgModal').attr('src', $(images[images.length - 1]).attr('data-detail-src'));
  }
}

// 이미지 모달 오른쪽 화살표 버튼 핸들러
function handlerNextImg() {
  let wrapperSelector = '';
  switch(MODAL_COLLER) {
  	case 'DETAIL':
  	  wrapperSelector = '#detailAttachment'
  	  break;
  	case 'WRITE':
  	  wrapperSelector = '#uploadFilesAttachment'
  	  break;
  	case 'MODIFY':
  	  wrapperSelector = '#modifyUploadFilesAttachment'
  }
  const images = $(wrapperSelector + '.wrapper-img').children('.img-uploaded-file');
  const currentSrc = $('#imgModal').attr('src');

  let currentIndex;
  for (i = 0; i < images.length; i++) {
    // console.log($(images[i]).attr("data-detail-src"));
    // console.log(currentSrc);
    if ($(images[i]).attr('data-detail-src') == currentSrc) {
      currentIndex = i;
      break;
    }
  }

  // console.log(currentIndex)
  if (currentIndex < images.length - 1) {
    $('#imgModal').attr('src', $(images[currentIndex + 1]).attr('data-detail-src'));
  } else {
    $('#imgModal').attr('src', $(images[0]).attr('data-detail-src'));
  }
}

// 파일 업로드 후 업로드 된 파일들을 화면에 출력하는 함수
function showUploadedFile(data, elementId) {
  let output = '';

  $.each(data, function (i, e) {
    let fileName = e.new_file_name.replaceAll('\\', '/');
    if (e.thumbnail_file_name != null) {
      let thumb = e.thumbnail_file_name.replaceAll('\\', '/');
      // 이미지
      output += `<div class="wrapper-img" id="uploadFilesAttachment">`;
      output += `<img src="/resources/user/uploads/inquiry${thumb}" data-detail-src="/resources/user/uploads/inquiry${fileName}" id="${e.new_file_name}" class="img-uploaded-file img-clickable" onclick="viewDetailImg('/resources/user/uploads/inquiry${fileName}', '', 'WRITE')" />`;
    } else {
      output += `<a href="/resources/user/uploads/inquiry${fileName}" id="${e.new_file_name}">${e.original_file_name}</a>`;
    }
    output += `<img src="/resources/user/images/kyj/remove.png" class="icon" onclick="removeFile(this);" />`;
    output += `</div>`;
  });

  $('#' + elementId).html(output);
}

// 답글 작성 시 값을 서버에 전달하기 전 유효성 검사
function validation() {
  const elementTitle = $('input[name="title"]');
  // const inquiryCategoryNo = $('input[name="inquiryCategoryNo"]').val();
  const elementContent = $('textarea[name="content"]');

  let isValid = true;

  if (elementTitle.val().length < 1) {
    toastrWarning('필수 입력 사항', '제목은 필수 입력 사항입니다.');
    elementTitle.focus();
    isValid = false;
  }

  if (elementContent.val().length < 1) {
    toastrWarning('필수 입력 사항', '내용은 필수 입력 사항입니다.');
    elementContent.focus();
    isValid = false;
  }

  if (isValid) {
    $(".fileListJSONString").val(JSON.stringify(FILE_LIST));
    return true;
  } else {
    return false;
  }
}

// 문의글 수정 시 값을 서버에 전달하기 전 유효성 검사
function validationModify() {
  const elementTitle = $('#modifyInquiryTitle');
  const elementCategory = $('#modifyInquiryCategory');
  const elementContent = $('#modifyInquiryContent');

  let isValid = true;

  if (elementTitle.val().length < 1) {
    toastrWarning('필수 입력 사항', '제목은 필수 입력 사항입니다.');
    elementTitle.focus();
    isValid = false;
  }

  if (elementCategory.val() == -1) {
    toastrWarning('필수 선택 사항', '분류는 필수 선택 사항입니다.');
    elementTitle.focus();
    isValid = false;
  }

  if (elementContent.val().length < 1) {
    toastrWarning('필수 입력 사항', '내용은 필수 입력 사항입니다.');
    elementContent.focus();
    isValid = false;
  }

  if (isValid) {
    $(".fileListJSONString").val(JSON.stringify(FILE_LIST));
    return true;
  } else {
    return false;
  }
}

// 뮨의 상세 창의 답글 달기 버튼 핸들러
function showWriterForm() {
  FILE_LIST = [];
  if (!$('#inquiryWrite').hasClass('pop')) {
    $('#inquiryWrite').addClass('pop');
  }
  document.getElementById('inquiryWrite').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 문의 상세 창의 문의글 수정 버튼 핸들러
function showModifyForm() {
  FILE_LIST = FILE_LIST_OF_CURRENT_INQUIRY;
  $('#inquiryDetail').removeClass('pop');
  $('#inquiryWrite').removeClass('pop');

  let title = $('#inquiryTitle').html();
  let lastSpanIndex = title.lastIndexOf('</span>');
  if (lastSpanIndex != -1) {
    title = title.substring(lastSpanIndex + '</span>'.length);
  }

  $('#modifyInquiryTitle').val(title);
  $('#modifyInquiryCategory').val($('#inquiryCategoryName').attr('data-category-no'));
  $('#modifyInquiryCategory').next().children().children().children().html($('#inquiryCategoryName').text());
  $('#modifyInquiryContent').val($('#inquiryContent').html());

  if (!$('#inquiryModify').hasClass('pop')) {
    $('#inquiryModify').addClass('pop');
  }
  document.getElementById('inquiryModify').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 답글 달기 창 취소 시 업로드 파일을 모두 삭제한 후 실행될 입력폼 초기화 함수
function resetForm() {
  const writeForm = document.getElementById('writeForm');
  writeForm.reset();
  $('#uploadFiles').html('');
  $('#inquiryWrite').removeClass('pop');
}

/* === ajax ==================================================================================================================== */
// 검색 버튼 핸들러에서 호출 될 api 요청 함수
function getInquiryBoardList(params) {
  // console.log(params);
  $.ajax({
    url: '/admin/board/inquiry/data',
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
function getInquiryDetail(inquiryBoardNo) {
  $.ajax({
    url: '/admin/board/inquiry/detail',
    type: 'get',
    data: {
      inquiryBoardNo,
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

// 업로드한 이미지의 제거 버튼 핸들러(답글작성)
function removeFile(evtElement) {
  let removeFile = $(evtElement).prev().attr('id');
  // console.log(removeFile);
  let fileListJSONString = JSON.stringify(FILE_LIST);
  // console.log(fileListJSONString);

  $.ajax({
    url: '/admin/board/inquiry/removeFile',
    type: 'get',
    data: {
      removeFile,
      fileListJSONString,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      if (data.message == 'success') {
        FILE_LIST = data.fileList;
        $(evtElement).parent().remove();
      }
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '첨부 사진 삭제 작업 중에 오류가 발생했습니다.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 업로드한 이미지의 제거 버튼 핸들러(글수정)
function toBeRemoveFile(evtElement) {
  let removeFile = $(evtElement).prev().attr('id');
  let inquiryBoardNo = $('#formModifyInquiryBoardNo').val();
  // console.log(removeFile);
  let fileListJSONString = JSON.stringify(FILE_LIST);

  $.ajax({
    url: '/admin/board/inquiry/toBeRemoveFile',
    type: 'get',
    data: {
      removeFile: removeFile,
      inquiryBoardNo,
      fileListJSONString
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      if (data.message == 'success') {
        FILE_LIST = data.fileList;
        $(evtElement).parent().remove();
      }
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '임시 업로드 파일들을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 답글 달기 취소 버튼 핸들러
function removeAllFile() {
  let fileListJSONString = JSON.stringify(FILE_LIST);
  
  $.ajax({
    url: '/admin/board/inquiry/removeAllFile',
    type: 'get',
    data: {
      fileListJSONString
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      if (data.message == 'success') {
        FILE_LIST = [];
        resetForm();
      }
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '임시 업로드 파일들을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 문의글 수정 취소 버튼 핸들러
function toBeRemoveAllFile() {
  let inquiryBoardNo = $('#formModifyInquiryBoardNo').val();
  $.ajax({
    url: '/admin/board/inquiry/toBeRemoveAllFile',
    type: 'get',
    data: {
      inquiryBoardNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      if (data.message == 'success') {
        FILE_LIST = data.fileList;
        printModifyUploadFiles(data.fileList);
      }
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('수정 실패', '문의글을 수정하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 문의글 삭제 버튼 핸들러
function removeInquiry() {
  let inquiryBoardNo = $('#inquiryBoardNo').text();
  // console.log(inquiryBoardNo);
  $.ajax({
    url: '/admin/board/inquiry/remove',
    type: 'post',
    data: {
      inquiryBoardNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('삭제 완료', '문의글이 삭제되었습니다. 글번호: ' + inquiryBoardNo);

      search();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '문의글을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
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

  printSearchResultTable(data.inquiryBoardList);
}

// searchResult table에 매개 변수로 넘겨받은 값을 기준으로 동적 출력
function printSearchResultTable(data) {
  if (data.length > 0) {
    let output = '';
    data.forEach(function (item, index) {
      let title = '';
      for (let i = 0; i < item.step; i++) {
        title += '<span>RE: </span>';
      }
      title += item.title;
      let writer = item.member_id != null ? item.member_id : item.email != null ? item.email : item.admin_id;
      let createdDate = new Date(item.created_date).toLocaleDateString();
      // let updatedDate = item.updated_date != null ? new Date(item.updated_date).toLocaleDateString() : "-";

      let status =
        item.step == 0 && item.tree_count == 1
          ? `<span class="badge light badge-danger"><i class="fa fa-circle text-danger mr-1"></i>응답대기</span>`
          : item.step == 0
          ? `<span class="badge light badge-success"><i class="fa fa-circle text-success mr-1"></i>응답완료</span>`
          : `<span class="badge light badge-secondary"><i class="fa fa-circle text-secondary mr-1"></i>응답글</span>`;

      output += `<tr onclick="getInquiryDetail(${item.inquiry_board_no});">`;
      output += `<td>${item.inquiry_board_no}</td>`;
      output += `<td>${item.inquiry_category_name}</td>`;
      output += `<td class="board-title">${title}</td>`;
      output += `<td>${writer}</td>`;
      output += `<td>${createdDate}</td>`;
      output += `<td>${status}</td>`;
      output += '</tr>';
    });

    $('#searchResult').html(output);
  } else {
    printEmptyTable('조건에 일치하는 검색결과가 없습니다.', 6);
  }
}

// ajax 요청이 success 시 상세 문의글을 화면에 출력시키기 위한 함수
function printDetail(data) {
  resetForm();

  let title = '';
  for (let i = 0; i < data.inquiry.step; i++) {
    title += '<span>RE: </span>';
  }
  title += data.inquiry.title;
  let writer =
    data.inquiry.member_id != null
      ? data.inquiry.member_id
      : data.inquiry.email != null
      ? data.inquiry.email
      : data.inquiry.admin_id;
  let createdDate = new Date(data.inquiry.created_date).toLocaleDateString();
  let updatedDate = data.inquiry.updated_date != null ? new Date(data.inquiry.updated_date).toLocaleDateString() : '-';

  let status =
    data.inquiry.step == 0 && data.inquiry.tree_count == 1
      ? `<span class="badge light badge-danger"><i class="fa fa-circle text-danger mr-1"></i>응답대기</span>`
      : data.inquiry.step == 0
      ? `<span class="badge light badge-success"><i class="fa fa-circle text-success mr-1"></i>응답완료</span>`
      : `<span class="badge light badge-secondary"><i class="fa fa-circle text-secondary mr-1"></i>응답글</span>`;

  let images = '',
    imagesInModify = '';
  if (data.inquiryImages.length > 0) {
    FILE_LIST_OF_CURRENT_INQUIRY = data.inquiryImages;
    data.inquiryImages.forEach(function (item) {
      let thumbnailFileName = item.thumbnail_file_name.replaceAll('\\', '/');
      let newFileName = item.new_file_name.replaceAll('\\', '/');

      images += `<div class="wrapper-img" id="detailAttachment">`;
      images += `<img `;
      images += `src='/resources/user/uploads/inquiry${thumbnailFileName}'`;
      images += `data-detail-src="/resources/user/uploads/inquiry${newFileName}"`;
      images += `id="img${item.inquiry_images_no}"`;
      images += `class="img-uploaded-file img-clickable"`;
      images += `onclick="viewDetailImg('/resources/user/uploads/inquiry${newFileName}', '${item.original_file_name}', 'DETAIL');"`;
      images += ` />`;
      images += `</div>`;
    });

    printModifyUploadFiles(data.inquiryImages);
  }

  $('#responseInquiryBoardNo').html(data.inquiry.inquiry_board_no);
  $('#responseInquiryRef').val(data.inquiry.ref);
  $('#responseInquiryStep').val(data.inquiry.step);
  $('#responseInquiryRefOrder').val(data.inquiry.ref_order);
  $('#responseInquiryCategoryName').html(data.inquiry.inquiry_category_name);
  $('#responseInquiryCategoryNo').val(data.inquiry.inquiry_category_no);

  $('#formModifyInquiryBoardNo').val(data.inquiry.inquiry_board_no);
  $('#modifyInquiryBoardNo').html(data.inquiry.inquiry_board_no);
  $('#modifyInquiryCategory').val(data.inquiry.inquiry_category_no);
  $('#modifyInquiryCategory').next().children().children().children().html(data.inquiry.inquiry_category_name);
  $('#modifyInquiryWriter').html(writer);
  $('#modifyInquiryTitle').val(data.inquiry.title);
  $('#modifyInquiryContent').val(data.inquiry.content);

  $('#inquiryBoardNo').html(data.inquiry.inquiry_board_no);
  $('#inquiryCategoryName').html(data.inquiry.inquiry_category_name);
  $('#inquiryCategoryName').attr('data-category-no', data.inquiry.inquiry_category_no);
  $('#inquiryWriter').html(writer);
  $('#inquiryCreatedDate').html(createdDate);
  $('#inquiryUpdatedDate').html(updatedDate);
  $('#inquiryStatus').html(status);
  $('#inquiryTitle').html(title);
  $('#inquiryContent').html(data.inquiry.content);

  $('#inquiryImages').html(images);

  if (!$('#inquiryDetail').hasClass('pop')) {
    $('#inquiryDetail').addClass('pop');
  }
  document.getElementById('inquiryDetail').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// 문의글 수정 창 닫기 시 업로드 파일 전체를 초기화 후 화면에 갱신하는 함수
function printModifyUploadFiles(data) {
  let imagesInModify = '';

  if (data.length > 0) {
    data.forEach(function (item) {
      imagesInModify += `<div class="wrapper-img" id="modifyUploadFilesAttachment">`;

      let thumbnailFileName = item.thumbnail_file_name.replaceAll('\\', '/');
      let newFileName = item.new_file_name.replaceAll('\\', '/');

      imagesInModify += `<img `;
      imagesInModify += `src='/resources/user/uploads/inquiry${thumbnailFileName}'`;
      imagesInModify += `data-detail-src="/resources/user/uploads/inquiry${newFileName}"`;
      imagesInModify += `id="${item.new_file_name}"`;
      imagesInModify += `class="img-uploaded-file img-clickable"`;
      imagesInModify += `onclick="viewDetailImg('/resources/user/uploads/inquiry${newFileName}', '${item.original_file_name}', 'MODIFY');"`;
      imagesInModify += ` />`;
      imagesInModify += `<img src="/resources/user/images/kyj/remove.png" class="icon" onclick="toBeRemoveFile(this);" />`;
      imagesInModify += `</div>`;
    });
  }

  $('#modifyUploadFiles').html(imagesInModify);

  $('#inquiryModify').removeClass('pop');
  $('#inquiryDetail').addClass('pop');
}
