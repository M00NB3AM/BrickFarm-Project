// ajax 요청 시 넘기는 파라메터
const params = {
  startDate: '',
  endDate: '',
  recentWeek: 1,
};

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

// 키, 값을 매개변수로 받아 파라메터 변수를 바꿔주는 Setter 함수
function setParams(key, value) {
  params[key] = value;
}

// 기간 선택 핸들러
function dateRangePickerChangeHandler(e, picker) {
  const startDate = picker.startDate.format('YYYY-MM-DD');
  const endDate = picker.endDate.format('YYYY-MM-DD');
  setParams('startDate', startDate);
  setParams('endDate', endDate);
}

/* === print ==================================================================================================================== */
// 최근 n 주 기간 Select의 옵션 출력
function printRecentWeekSelect() {
  let output = '';
  for (let i = 1; i < 53; i++) {
    output += `<option value="${i}">${i}</option>`;
  }
  $('#recentWeek').html(output);
}

// searchResult table에 매개 변수로 넘겨받은 메시지와 함께 2번째 매개변수 크기로 병합된 하나의 빈 컬럼을 출력하여줌
function printEmptyTable(msg, colspan) {
  const value = msg != undefined ? msg : '조건을 설정하고 검색을 진행하여 목록을 받아보실 수 있습니다.';
  let output = `<tr class="empty-row"><td colspan="${colspan}">${value}</td></tr>`;
  $('#searchResult').html(output);
}
