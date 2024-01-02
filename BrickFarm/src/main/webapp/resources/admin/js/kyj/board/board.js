// ajax 요청 시 넘기는 파라메터
const params = {
  curPageNo: 1,
  rowCountPerPage: 10,

  startDate: '',
  endDate: '',

  categoryNo: -1,

  /*  
  memberId: '',		//like
  memberName: '',	//like
  adminId: '',		//like
  boardNo: -1,		//correct
  title: '',		//like
  content: '',		//like
  */
  searchKey: '',
  searchValue: '',

  isNeedResponse: false,
};

const CATEGORY_API_URL = {
  NOTICE: '/admin/board/notice/category',
  INQUIRY: '/admin/board/inquiry/category',
  FAQ: '/admin/board/faq/category',
};

const CATEGORY_COLUMNS = {
  NOTICE: {
    NO: 'notice_category_no',
    NAME: 'notice_category_name',
  },
  INQUIRY: {
    NO: 'inquiry_category_no',
    NAME: 'inquiry_category_name',
  },
  FAQ: {
    NO: 'faq_category_no',
    NAME: 'faq_category_name',
  },
};

// 키, 값을 매개변수로 받아 파라메터 변수를 바꿔주는 Setter 함수
function setParams(key, value) {
  params[key] = value;
}

// 파라메터의 기간 값을 date-range-picker로부터 얻어와 초기화
function initDateParams() {
  const startDate = $('#dateRangePicker').val().split(' - ')[0];
  const endDate = $('#dateRangePicker').val().split(' - ')[1];
  setParams('startDate', startDate);
  setParams('endDate', endDate);
}

// date-range-picker init
function initDateRangePicker() {
  let now = new Date();
  let before = new Date(now - 1000 * 60 * 60 * 24 * 7);

  /* https://www.daterangepicker.com/#options */

  $('.input-daterange-datepicker').daterangepicker({
    buttonClasses: ['btn', 'btn-sm'],
    applyClass: 'btn-danger',
    cancelClass: 'btn-inverse',
    startDate: before,
    endDate: now,
    locale: {
      format: 'YYYY-MM-DD',
      separator: ' - ',
      applyLabel: '선택',
      cancelLabel: '취소',
      daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
      monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    },
  });
}

// 페이지 선택 핸들러
function selectPage(pageNo) {
  setParams('curPageNo', pageNo);
  search();
}

// 기간 선택 핸들러
function dateRangePickerChangeHandler(e, picker) {
  const startDate = picker.startDate.format('YYYY-MM-DD');
  const endDate = picker.endDate.format('YYYY-MM-DD');
  setParams('startDate', startDate);
  setParams('endDate', endDate);
}

// 페이지당 보여줄 row의 개수 선택 핸들러
function selectRowCountPerPage(selectedValue) {
  setParams('rowCountPerPage', selectedValue);
  search();
}

// api에서 받아온 게시판 분류 목록을 Select의 option 태그로 출력
function initCategoryList(classification) {
  getBoardCategory(classification);
}

// 상품 분류 목록 Select tag를 출력 후 이벤트 리스너를 등록하기 위한 함수
function addCategorySelectEventListener() {
  $('#categoryNo').on('change', function (e) {
    setParams('categoryNo', e.target.value);
  });
}

/* === ajax ==================================================================================================================== */
// api를 호출하여 게시판 분류 목록을 얻어와 해당 화면 출력 함수에게 넘겨줌
function getBoardCategory(classification) {
  $.ajax({
    url: CATEGORY_API_URL[classification],
    type: 'get',
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printBoardCategorySelect(data.category, classification);
      if (classification == 'INQUIRY') {
        printBoardCategorySelectToElementId(data.category, classification, 'modifyInquiryCategory');
      }
      if (classification == 'NOTICE') {
        printBoardCategorySelectToElementId(data.category, classification, 'modifyNoticeCategory');
        printBoardCategorySelectToElementId(data.category, classification, 'writeNoticeCategory');
      }
      if (classification == 'FAQ') {
        printBoardCategorySelectToElementId(data.category, classification, 'modifyFaqCategory');
        printBoardCategorySelectToElementId(data.category, classification, 'writeFaqCategory');
      }
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

/* === print ==================================================================================================================== */
// api 요청 후 넘어온 paginationInfo 값을 매개변수로 받아 그 값을 기준으로 페이지네이션 동적 출력
function printPagination(paginationInfo) {
  let output = '';

  if (paginationInfo.curBlockNo == 1) {
    output += `<a class="pagination_button previous disabled" id="previous">이전</a>`;
  } else {
    output += `<a class="pagination_button previous" id="previous" onclick="selectPage(${
      (paginationInfo.curBlockNo - 1) * paginationInfo.pageCountPerBlock - (paginationInfo.pageCountPerBlock - 1)
    });">이전</a>`;
  }

  output += `<span>`;
  for (let i = paginationInfo.startPageIndex; i <= paginationInfo.endPageIndex; i++) {
    if (paginationInfo.curPageNo == i) {
      output += `<a class="pagination_button current">${i}</a>`;
    } else {
      output += `<a class="pagination_button" onclick="selectPage(${i});">${i}</a>`;
    }
  }
  output += `</span>`;

  if (paginationInfo.curBlockNo < paginationInfo.totalPageBlockCount) {
    output += `<a class="pagination_button next" id="previous" onclick="selectPage(${
      (paginationInfo.curBlockNo + 1) * paginationInfo.pageCountPerBlock - (paginationInfo.pageCountPerBlock - 1)
    });">다음</a>`;
  } else {
    output += `<a class="pagination_button next disabled" id="previous">다음</a>`;
  }

  $('#pagination').html(output);
}

// 매개 변수로 넘겨받은 값을 기준으로 게시판 분류 select의 options element 동적 출력
function printBoardCategorySelect(categoryList, classification) {
  let output = `<option value="-1">분류</option>`;
  categoryList.forEach(function (item) {
    output += `<option value="${item[CATEGORY_COLUMNS[classification].NO]}">${
      item[CATEGORY_COLUMNS[classification].NAME]
    }</option>`;
  });
  $('#categoryNo').html(output);
  addCategorySelectEventListener();
}

// 매개 변수로 넘겨받은 값을 기준으로 게시판 분류 select의 options element 동적 출력
function printBoardCategorySelectToElementId(categoryList, classification, elementId) {
  let output = `<option value="-1">분류</option>`;
  categoryList.forEach(function (item) {
    output += `<option value="${item[CATEGORY_COLUMNS[classification].NO]}">${
      item[CATEGORY_COLUMNS[classification].NAME]
    }</option>`;
  });
  $('#' + elementId).html(output);
}

// searchResult table에 매개 변수로 넘겨받은 메시지와 함께 2번째 매개변수 크기로 병합된 하나의 빈 컬럼을 출력하여줌
function printEmptyTable(msg, colspan) {
  const value = msg != undefined ? msg : '조건을 설정하고 검색을 진행하여 목록을 받아보실 수 있습니다.';
  let output = `<tr class="empty-row"><td colspan="${colspan}">${value}</td></tr>`;
  $('#searchResult').html(output);
}
