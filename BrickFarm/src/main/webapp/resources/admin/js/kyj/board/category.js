// 현재 열려있는 입력폼의 정보를 담을 객체
const INPUT_STATE = {
  CLASSIFICATION: 'NOTICE',
  MODE: 'CREATE',
};

// 분류 목록 요청 시 사용 될 api 목록 상수
const CATEGORY_API_URL = {
  NOTICE: {
    CREATE: '/admin/board/notice/category/create',
    GET: '/admin/board/notice/category',
    MODIFY: '/admin/board/notice/category/modify',
    REMOVE: '/admin/board/notice/category/remove',
  },
  INQUIRY: {
    CREATE: '/admin/board/inquiry/category/create',
    GET: '/admin/board/inquiry/category',
    MODIFY: '/admin/board/inquiry/category/modify',
    REMOVE: '/admin/board/inquiry/category/remove',
  },
  FAQ: {
    CREATE: '/admin/board/faq/category/create',
    GET: '/admin/board/faq/category',
    MODIFY: '/admin/board/faq/category/modify',
    REMOVE: '/admin/board/faq/category/remove',
  },
};

// 분류 목록 요청 후 받은 response로 화면에 출력 시 사용될 상수
const CATEGORY_COLUMNS = {
  NOTICE: {
    NO: 'notice_category_no',
    NAME: 'notice_category_name',
    ELEMENT_ID: '#noticeCategoryList',
  },
  INQUIRY: {
    NO: 'inquiry_category_no',
    NAME: 'inquiry_category_name',
    ELEMENT_ID: '#inquiryCategoryList',
  },
  FAQ: {
    NO: 'faq_category_no',
    NAME: 'faq_category_name',
    ELEMENT_ID: '#faqCategoryList',
  },
};

// 열린 입력폼의 타겟 게시판을 설정하는 Setter
function setInputClassification(value) {
  INPUT_STATE.CLASSIFICATION = value;
}

// 열린 입력폼의 상태(작성/수정)를 설정하는 Setter
function setInputMode(value) {
  INPUT_STATE.MODE = value;
}

// 게시판 별로 각 분류 목록 요청 api를 일괄적으로 실행시키기 위한 함수
function initAllCategoryList() {
  getBoardCategory('NOTICE');
  getBoardCategory('INQUIRY');
  getBoardCategory('FAQ');
}

// 분류 추가 혹은 수정 버튼 클릭 시 입력폼을 보여주기 위한 함수
function showInputForm() {
  $('#inputForm').addClass('pop');
  $('#createBtn').removeClass('pop');
}

// 입력폼 취소 시 분류 추가 버튼을 다시 보여주기 위한 함수
function showCreateBtn() {
  $('#createBtn').addClass('pop');
  $('#inputForm').removeClass('pop');
}

// 입력폼 수락 버튼 핸들러
function acceptHandler() {
  // console.log(INPUT_STATE);
  const categoryNo = $('#inputNo').val();
  const categoryName = $('#inputName').val();

  switch (INPUT_STATE.MODE) {
    case 'CREATE':
      createBoardCategory(INPUT_STATE.CLASSIFICATION, categoryName);
      break;
    case 'MODIFY':
      modifyBoardCategory(INPUT_STATE.CLASSIFICATION, categoryNo, categoryName);
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// 입력폼 취소 버튼 핸들러
function cancelHandler() {
  $('#inputNo').val('');
  $('#inputName').val('');
  showCreateBtn();
}

// + (분류 추가) 버튼 핸들러
function createHandler() {
  setInputMode('CREATE');
  showInputForm();
}

// 분류 수정 버튼 핸들러
function modifyHandler(classification, categoryNo, categoryName) {
  // console.log(classification, categoryNo, categoryName);
  $('#inputNo').val(categoryNo);
  $('#inputName').val(categoryName);
  setInputMode('MODIFY');
  showInputForm();
}

// 분류 삭제 버튼 핸들러
function removeCategory(classification, categoryNo) {
  // console.log(classification, categoryNo);
  removeBoardCategory(classification, categoryNo);
}

/* === ajax ==================================================================================================================== */
// api를 호출하여 게시판 분류 목록을 얻어와 해당 화면 출력 함수에게 넘겨줌
function getBoardCategory(classification) {
  $.ajax({
    url: CATEGORY_API_URL[classification].GET,
    type: 'get',
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printBoardCategoryList(data.category, classification);
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

// 분류 수정 요청 api
function modifyBoardCategory(classification, categoryNo, categoryName) {
  $.ajax({
    url: CATEGORY_API_URL[classification].MODIFY,
    type: 'post',
    data: {
      categoryNo,
      categoryName,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('수정 완료', '분류 정보가 수정되었습니다.');

      getBoardCategory(classification);
      cancelHandler();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('수정 실패', '분류 수정 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 분류 삭제 요청 api
function removeBoardCategory(classification, categoryNo) {
  $.ajax({
    url: CATEGORY_API_URL[classification].REMOVE,
    type: 'post',
    data: {
      categoryNo,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('삭제 완료', '선택한 분류가 삭제되었습니다.');

      getBoardCategory(classification);
      cancelHandler();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('삭제 실패', '분류 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// 분류 작성 요청 api
function createBoardCategory(classification, categoryName) {
  $.ajax({
    url: CATEGORY_API_URL[classification].CREATE,
    type: 'post',
    data: {
      categoryName,
    },
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);
      toastrSuccess('추가 완료', '분류가 추가되었습니다.');

      getBoardCategory(classification);
      cancelHandler();
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('추가 실패', '분류 추가 중 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

/* === print ==================================================================================================================== */
// 매개 변수로 넘겨받은 값을 기준으로 게시판 분류 목록 동적 출력
function printBoardCategoryList(categoryList, classification) {
  let output = '';
  categoryList.forEach(function (item) {
    output += `<li class="list-group-item d-flex justify-content-between align-items-center py-2 pr-2">`;
    output += item[CATEGORY_COLUMNS[classification].NAME];
    output += `<div>`;
    output += `<button class="btn btn-primary btn-list-radius p-2 mr-2" type="button" onclick="modifyHandler('${classification}', ${
      item[CATEGORY_COLUMNS[classification].NO]
    }, '${item[CATEGORY_COLUMNS[classification].NAME]}');">수정</button>`;
    output += `<button class="btn btn-danger btn-list-radius p-2" type="button" onclick="removeCategory('${classification}', ${
      item[CATEGORY_COLUMNS[classification].NO]
    });">삭제</button>`;
    output += `</div>`;
    output += `</li>`;
  });

  $(CATEGORY_COLUMNS[classification].ELEMENT_ID).html(output);
  //addCategorySelectEventListener();
}
