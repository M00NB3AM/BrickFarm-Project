// ajax 요청 시 넘기는 파라메터
const params = {
  curPageNo: 1,
  rowCountPerPage: 10,

  startDate: '',
  endDate: '',
  productLargeCategoryNo: -1,
  productMediumCategoryNo: -1,
  productSmallCategoryNo: -1,
  productCode: '',
  productName: '',
  startPartsQuantity: -1,
  endPartsQuantity: -1,
  recommendAges: [],
  startPrice: -1,
  endPrice: -1,
  memberId: '',
  memberName: '',

  orderByColumnName: '',
};

// 상품 분류 깊이
const CATEGORY_LEVEL = {
  LARGE: 0,
  MEDIUM: 1,
  SMALL: 2,
};

// 상품 분류 목록 (메뉴 생성을 위한 배열)
const categoryList = [];

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

// 파라메터의 정렬 기준 값을 셋팅하는 함수 (온로드 초기화용)
function initOrderByColumnName(value) {
  setParams('orderByColumnName', value);
}

// 키, 값을 매개변수로 받아 파라메터 변수를 바꿔주는 Setter 함수
function setParams(key, value) {
  params[key] = value;
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

// 값과, 분류 깊이를 매개변수로 받아 상품 분류 파라메터를 세팅하고 상황에 따라 Select tag를 다시 출력하도록 필요한 함수를 호출하는 핸들러
function selectProductCategory(selectedValue, categoryLevel) {
  switch (categoryLevel) {
    case CATEGORY_LEVEL.LARGE:
      setParams('productLargeCategoryNo', selectedValue);
      setParams('productMediumCategoryNo', -1);
      setParams('productSmallCategoryNo', -1);
      if (selectedValue != -1) {
        printProductCategorySelect(CATEGORY_LEVEL.MEDIUM, parseInt(selectedValue));
        addCategorySelectEventListener('productMediumCategoryNo', CATEGORY_LEVEL.MEDIUM);
        $('#wrapperProductMediumCategoryNo').show();
        $('#wrapperProductSmallCategoryNo').hide();
      } else {
        $('#wrapperProductMediumCategoryNo').hide();
        $('#wrapperProductSmallCategoryNo').hide();
      }
      break;
    case CATEGORY_LEVEL.MEDIUM:
      setParams('productMediumCategoryNo', selectedValue);
      setParams('productSmallCategoryNo', -1);
      if (selectedValue != -1) {
        printProductCategorySelect(CATEGORY_LEVEL.SMALL, parseInt(selectedValue));
        addCategorySelectEventListener('productSmallCategoryNo', CATEGORY_LEVEL.SMALL);
        $('#wrapperProductSmallCategoryNo').show();
      } else {
        $('#wrapperProductSmallCategoryNo').hide();
      }
      break;
    case CATEGORY_LEVEL.SMALL:
      setParams('productSmallCategoryNo', parseInt(selectedValue));
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// api에서 받아온 상품 분류 목록을 현재 이 페이지의 전역 변수에 할당하고 대분류 Select tag를 동적으로 출력
function initCategoryList(list) {
  list.forEach(function (item) {
    categoryList.push(item);
  });
  $('#wrapperProductMediumCategoryNo').hide();
  $('#wrapperProductSmallCategoryNo').hide();

  printProductCategorySelect(CATEGORY_LEVEL.LARGE);
  addCategorySelectEventListener('productLargeCategoryNo', CATEGORY_LEVEL.LARGE);
}

// 상품 분류 목록 Select tag를 출력 후 이벤트 리스너를 등록하기 위한 함수
function addCategorySelectEventListener(elementId, categoryLevel) {
  $('#' + elementId).on('change', function (e) {
    selectProductCategory(e.target.value, categoryLevel);
  });
}

/* === ajax ==================================================================================================================== */
// api를 호출하여 상품 분류 목록을 얻어와 해당 화면 출력 함수에게 넘겨줌
function getProductCategory() {
  $.ajax({
    url: '/admin/product/category',
    type: 'get',
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      initCategoryList(data.category);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      toastrError('로딩 오류', '상품 분류 목록 로딩 중 오류가 발생했습니다. 다시 시도해 주세요.');
    },
    complete: function () {
      // console.log(`[${this.url}] request complete`);
    },
  });
}

// api를 호출하여 추천 연령 목록을 얻어와 해당 화면 출력 함수에게 넘겨줌
function getProductRecommendAge() {
  $.ajax({
    url: '/admin/statistics/products/totalsales/recommendage',
    type: 'get',
    dataType: 'json',
    async: false,
    success: function (data) {
      // console.log(`[${this.url}] request success`);
      // console.log(data);

      printProductCategoryRecommendAgeCheckBox(data);
    },
    error: function (err) {
      // console.log(`[${this.url}] request error`);
      // console.log(err);
      if(err.responseJSON != null) {
        toastrError('오류 발생 : ' + err.responseJSON.errorCode, err.responseJSON.errorMessage);
      } else {
        toastrError('로딩 오류', '추천 연령 목록 로딩 중 오류가 발생했습니다. 다시 시도해 주세요.');
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

// 매개 변수로 넘겨받은 값을 기준으로 상품 분류 select의 options element 동적 출력
function printProductCategorySelect(categoryLevel, upperCategoryNo) {
  // console.log(categoryLevel, upperCategoryNo);
  let output = `<div class="form-group mb-0">`;
  switch (categoryLevel) {
    case CATEGORY_LEVEL.LARGE:
      output += `<select class="form-control" id="productLargeCategoryNo">`;
      output += `<option value="-1">대분류</option>`;
      categoryList.forEach(function (item) {
        if (item.category_level == 'Large') {
          output += `<option value="${item.product_category_no}">${item.product_category_name}</option>`;
        }
      });
      output += `</select>`;
      output += `</div>`;
      $('#wrapperProductLargeCategoryNo').html(output);
      break;
    case CATEGORY_LEVEL.MEDIUM:
      output += `<select class="form-control" id="productMediumCategoryNo">`;
      output += `<option value="-1">중분류</option>`;
      categoryList.forEach(function (item) {
        if (item.category_level == 'Medium' && item.upper_category_no == upperCategoryNo) {
          output += `<option value="${item.product_category_no}">${item.product_category_name}</option>`;
        }
      });
      output += `</select>`;
      output += `</div>`;
      $('#wrapperProductMediumCategoryNo').html(output);
      break;
    case CATEGORY_LEVEL.SMALL:
      output += `<select class="form-control" id="productSmallCategoryNo">`;
      output += `<option value="-1">소분류</option>`;
      categoryList.forEach(function (item) {
        if (item.category_level == 'Small' && item.upper_category_no == upperCategoryNo) {
          output += `<option value="${item.product_category_no}">${item.product_category_name}</option>`;
        }
      });
      output += `</select>`;
      output += `</div>`;
      $('#wrapperProductSmallCategoryNo').html(output);
      break;
    default:
      alert('!!! need debug !!!');
  }
}

// 매개 변수로 넘겨받은 값을 기준으로 추천 연령 체크박스 element들을 동적 출럭
function printProductCategoryRecommendAgeCheckBox(data) {
  // console.log(data);
  let output = '';
  data.recommendAge.forEach(function (item) {
    output += `<div>`;
    output += `<input id="check-${item}" class="form-check-input recommendAges mt-1" type="checkbox" value="${item}" />`;
    output += `<label class="form-check-label" for="check-${item}">${item}</label>`;
    output += `</div>`;
  });
  $('#recommendAgeGroup').html(output);
}

// searchResult table에 매개 변수로 넘겨받은 메시지와 함께 2번째 매개변수 크기로 병합된 하나의 빈 컬럼을 출력하여줌
function printEmptyTable(msg, colspan) {
  const value = msg != undefined ? msg : '조건을 설정하고 검색을 진행하여 목록을 받아보실 수 있습니다.';
  let output = `<tr class="empty-row"><td colspan="${colspan}">${value}</td></tr>`;
  $('#searchResult').html(output);
}
