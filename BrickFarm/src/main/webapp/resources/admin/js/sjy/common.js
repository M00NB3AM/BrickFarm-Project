// daterangepicker
$('.input-daterange-datepicker').daterangepicker({
    	"locale": {
	                "format": "YYYY-MM-DD",
	                "separator": " ~ ",
	                "applyLabel": "확인",
	                "cancelLabel": "취소",
	                "fromLabel": "From",
	                "toLabel": "To",
	                "customRangeLabel": "Custom",
	                "weekLabel": "W",
	                "daysOfWeek": ["월", "화", "수", "목", "금", "토", "일"],
	                "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
	            },
	    "firstDay": 1,
	    "drops": "down",
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-danger',
       	cancelClass: 'btn-inverse'
});
   
// 성공 alert
function successAlert(text) {
  Swal.fire(`${text}되었습니다.`, '', 'success').then(() => {
    location.reload();
  });
}

// 실패 alert
function failAlert(text) {
  Swal.fire(`${text} 실패했습니다.`, '', 'error');
}

// 카테고리 가져오기
function getCategory() {
  $.ajax({
    url: `/admin/product/category`,
    type: 'get',
    dataType: 'json',
    async: false,
    success: function (data) {
      categoryList = data.category;
    },
    error: function () {
      alert('error 발생');
    },
  });
}

// 이벤트 가져오기
function getEvent() {
  $.ajax({
    url: `/admin/product/event`,
    type: 'post',
    dataType: 'json',
    async: false,
    success: function (data) {
      eventList = data.eventList;
    },
    error: function () {
      alert('error 발생');
    },
  });
}

// 카테고리 출력
function displayCategory(e) {
  $(`${e} .first-category`).on('change', function () {
    let output = `<option value="" selected>2차 분류</option>`;
    if ($(`${e} .first-category option:selected`).val() == 1) {
      $(categoryList).each(function (index, item) {
        if (item.category_level == 'Medium') {
          output += `<option value="${item.product_category_no}">${item.product_category_name}</option>`;
        }
      });
    }

    $('select.third-category').html(`<option selected>3차 분류</option>`);
    $('select.second-category').html(output);
    $('select.second-category').selectpicker('refresh');
    $('select.third-category').selectpicker('refresh');
  });

  $(`${e} .second-category`).on('change', function () {
    let output = `<option value="" selected>3차 분류</option>`;
    let selected = $(`${e} .second-category option:selected`).val();
    $(categoryList).each(function (index, item) {
      if (
        selected == item.upper_category_no &&
        item.category_level == 'Small'
      ) {
        console.log(item);
        output += `<option value="${item.product_category_no}">${item.product_category_name}</option>`;
      }
    });
    $('select.third-category').html(output);
    $('select.third-category').selectpicker('refresh');
  });
}

// 체크 박스 
function checkAll(element) {
  if ($(`#check${element}All`).is(':checked'))
    $(`input[name=${element}]`).prop('checked', true);
  else $(`input[name=${element}]`).prop('checked', false);
}

function checkTest(e) {
  var total = $(`input[name=${e}]`).length;
  var checked = $(`input[name=${e}]:checked`).length;

  if (total != checked) $(`#check${e}All`).prop('checked', false);
  else $(`#check${e}All`).prop('checked', true);
}

// 페이징 출력
function displayPaging(pagingInfo, method) {
  let pagingMethod = method;
  let output = `
	  			<ul
				class="pagination pagination-gutter justify-content-center mt-3">`;
  if (pagingInfo.pageNo != 1) {
    output += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="${pagingMethod}(${pagingInfo.pageNo - 1})"><i
						class="la la-angle-left"></i></a></li>`;
  }
  for (
    let i = pagingInfo.startNumOfCurrentPagingBlock;
    i <= pagingInfo.endNumOfCurrentPagingBlock;
    i++
  ) {
    if (pagingInfo.pageNo == i) {
      output += `<li class="page-item active"><a class="page-link"
						 href='javascript:void(0)' onclick="${pagingMethod}(${i})">${i}</a></li>`;
    } else {
      output += `<li class="page-item"><a class="page-link"
						 href='javascript:void(0)' onclick="${pagingMethod}(${i})">${i}</a></li>`;
    }
  }
  if (pagingInfo.pageNo < pagingInfo.totalPageCnt) {
    output += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="${pagingMethod}(${pagingInfo.pageNo + 1})"><i
					class="la la-angle-right"></i> </a></li>`;
  }
  output += `</ul>`;
  $('.paging').html(output);
}

// 상품 모달
function getProductList(pageNo) {
  $.ajax({
    url: `/admin/product/getProducts?pageNo=${pageNo}`,
    type: 'post',
    async: false,
    contentType: 'application/json',
    success: function (data) {
      displaySearchedResult(data.productList, data.pagingInfo);
    },
    error: function (request, error) {
      alert('error 발생');
      console.log(
        'code:' +
          request.status +
          '\n' +
          'message: ' +
          request.responseText +
          '\n' +
          'error : ' +
          error
      );
    },
  });
}

// 상품 검색
function getSearchedProducts() {
  let categoryNo = 0;
  if (
    $('.search-modal select.first-category').val() == '' ||
    $('select.second-category').val() == ''
  ) {
    categoryNo = $('.search-modal select.first-category').val();
  } else if (
    $('.search-modal select.second-category').val() != '' &&
    $('select.third-category').val() == ''
  ) {
    categoryNo = $('.search-modal select.second-category').val();
  } else {
    categoryNo = $('.search-modal select.third-category').val();
  }
  let searchCondition = {
    search_type: $('.search-modal select.search-word option:selected').val(),
    search_word: $('.search-modal input.search-word').val(),
    product_category_no: categoryNo,
    product_regist_date: {
      min_date: $('.search-modal input.product_regist_date')
        .val()
        .split(' ~')[0],
      max_date: $('.search-modal input.product_regist_date')
        .val()
        .split('~ ')[1],
    },
    sales_status: {
      all: $('.search-modal input.all').is(':checked') && 'Y',
      display: $('.search-modal input.display').is(':checked') && 'Y',
      event_no: $('input.is-event').is(':checked') ? 9999 : 0,
      is_new: $('.search-modal input.is-new').is(':checked') && 'Y',
      not_display: $('.search-modal input.not-display').is(':checked') && 'N',
    },
    product_price: {
      minimum_price: $('.search-modal input.minimum-price').val(),
      maximum_price: $('.search-modal input.maximum-price').val(),
    },
    stock_search_type: $('.search-modal select.stock option:selected').val(),
    stock_quantity: {
      minimum_stock: $('.search-modal input.minimum-stock').val(),
      maximum_stock: $('.search-modal input.maximum-stock').val(),
    },
  };
  console.log(JSON.stringify(searchCondition));

  $.ajax({
    url: `/admin/product/getProductsByConditionPaging`,
    type: 'post',
    // dataType : 'json',
    data: JSON.stringify(searchCondition),
    async: false,
    contentType: 'application/json',
    success: function (data) {
      console.log(data);
      searchedProductList = [];
      pagingSearchProduct = data.searchCondition;
      displaySearchedResult(data.productList, data.pagingInfo);
      displaySearchedProductPaging(data.pagingInfo);
    },
    error: function (request, error) {
      alert('error 발생');
      console.log(
        'code:' +
          request.status +
          '\n' +
          'message: ' +
          request.responseText +
          '\n' +
          'error : ' +
          error
      );
    },
  });
}

// 상품 검색 페이징
function getSearchedProductByPage(pageNo) {

  $.ajax({
    url: `/admin/product/getProductsByConditionPaging?pageNo=${pageNo}`,
    type: 'post',
    data: JSON.stringify(pagingSearchProduct),
    async: false,
    contentType: 'application/json',
    success: function (data) {
      displaySearchedResult(data.productList, data.pagingInfo);
      displaySearchedProductPaging(data.pagingInfo);
    },
    error: function (request, error) {
      alert('error 발생');
      console.log(
        'code:' +
          request.status +
          '\n' +
          'message: ' +
          request.responseText +
          '\n' +
          'error : ' +
          error
      );
    },
  });
}

// 검색 결과 페이징 출력
function displaySearchedProductPaging(pagingInfo) {
  let output = `
	  			<ul
				class="pagination pagination-gutter justify-content-center mt-3">`;
  if (pagingInfo.pageNo != 1) {
    output += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="getSearchedProductByPage(${
            pagingInfo.pageNo - 1
          })"><i
						class="la la-angle-left"></i></a></li>`;
  }
  for (
    let i = pagingInfo.startNumOfCurrentPagingBlock;
    i <= pagingInfo.endNumOfCurrentPagingBlock;
    i++
  ) {
    if (pagingInfo.pageNo == i) {
      output += `<li class="page-item active"><a class="page-link"
						 href='javascript:void(0)' onclick="getSearchedProductByPage(${i})">${i}</a></li>`;
    } else {
      output += `<li class="page-item"><a class="page-link"
						 href='javascript:void(0)' onclick="getSearchedProductByPage(${i})">${i}</a></li>`;
    }
  }
  if (pagingInfo.pageNo < pagingInfo.totalPageCnt) {
    output += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="getSearchedProductByPage(${
            pagingInfo.pageNo + 1
          })"><i
					class="la la-angle-right"></i> </a></li>`;
  }
  output += `</ul>`;

  if (pagingInfo.totalPageCnt == 0) {
    $('.search-modal .paging').empty();
  } else {
    $('.search-modal .paging').html(output);
  }
}

// 상품 검색 모달 검색 조건 리셋
function resetProductsCondition() {
  $('.modal-body > table:nth-child(1) input').val('');
  $('.modal-body select.search-word')
    .val('product_code')
    .prop('selected', true);
  $('select.first-category').val('').prop('selected', true);
  $('select.second-category').html(
    `<option value="" selected>2차 분류</option>`
  );
  $('select.third-category').html(
    `<option value="" selected>3차 분류</option>`
  );
  displayCategory('.search-category');
  $('.search-table input').prop('checked', false);
  $('select.stock_quantity').val('stock').prop('selected', true);
  $('select').selectpicker('refresh');
  $('.search-result').empty();
  $('.product_regist_date').data('daterangepicker').setStartDate(new Date());
  $('.product_regist_date').data('daterangepicker').setEndDate(new Date());
  $('input.product_regist_date').val('');
}

// 상품 선택
function choiceProduct() {
        let productList = searchedProductList.filter((item, i) => {
          return (
            searchedProductList.findIndex((item2, j) => {
              return item.product_code === item2.product_code;
            }) === i
          );
        });

        let choiceProducts = [];
        $('.product_code:checked').each(function (index, item) {
          let product_code = $(item).attr('id').split('check-box-')[1];

          $(productList).each(function (index, item) {
            if (item.product_code == product_code) {
              choiceProducts.push(item);
            }
          });
        });

        resetProductsCondition();
        $('.search-modal').modal('hide');
        displayProducts(choiceProducts);
      }
