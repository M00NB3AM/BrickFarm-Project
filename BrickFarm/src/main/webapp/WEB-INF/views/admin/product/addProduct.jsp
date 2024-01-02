<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/user/images/logo/logo-1.png" />
<title>상품 등록</title>
<link
	href="${contextPath}/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />
<!-- Datatable -->
<link
	href="${contextPath}/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<link
	href="${contextPath}/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.css"
	rel="stylesheet" />
<style>
.form-control-sm {
	height: calc(1.5em + 0.5rem + 2px) !important;
}

.filter-option-inner-inner, .form-control-sm.form-control {
	font-size: 16px;
}

.event-box {
	display: flex;
	gap: 20px;
	align-items: center;
}

.row {
	align-items: center;
}

.custom-file-label::after {
	content: '등록' !important;
	height: auto !important;
}

.upload-area {
	height: 150px;
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-align: center;
	font-weight: 500;
	font-size: 14px;
	color: #cccccc;
	border: 3px dashed #1e38bb;
	border-radius: 10px;
}

.upload-area input {
	position: absolute;
	height: 100%;
	width: 100%;
	opacity: 0;
	top: 0;
	left: 0;
	cursor: pointer;
}

#multiple-container {
	display: flex;
	height: 100%;
}

#multiple-container div {
	position: relative;
	margin: 40px 20px 15px;
}

#multiple-container div::after {
	content: attr(data-file);
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	padding: 5px 0;
	color: #fff;
	background: rgba(0, 0, 0, 0.75);
	font-size: 10px;
	text-align: center;
	border-radius: 0 0 5px 5px;
}

#multiple-container a {
	position: absolute;
	right: 0;
	top: -20%;
}

.product-img {
	width: 150px;
	height: 150px;
	object-fit: cover;
	border-radius: 5px;
}

.form-group {
	margin-bottom: 0 !important;
}

#lego {
	position: fixed;
	width: 150px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 999;
}
</style>
</head>
<body>
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<div id="preloader">
		<div class="sk-three-bounce">
			<img id="lego" src="${contextPath}/resources/admin/images/lego.gif"
				alt="" />
		</div>
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>상품 등록</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a>
							</li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">상품 등록</a></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<form class="card-body form-valide">
								<div class="table-responsive">
									<table
										class="table table-responsive-sm overflow-hidden add-table">
										<tbody>
											<tr>
												<th class="col-2">상품코드</th>
												<td class="row">
													<div class="col-3 form-group">
														<input type="text"
															class="form-control-sm form-control product_code"
															id="product_code" name="product_code" />
													</div>
												</td>
											</tr>
											<tr>
												<th class="col-2">상품명</th>
												<td class="row">
													<div class="col-3 form-group">
														<input type="text"
															class="form-control-sm form-control product_name"
															id="product_name" name="product_name" />
													</div>
												</td>
											</tr>
											<tr>
												<th>카테고리</th>
												<td class="row search-category">
													<div class="col-2 form-group">
														<select
															class="first-category form-control form-control-sm"
															id="first-category" name="first-category">
															<option value="" selected>1차 분류</option>
															<option value="1">이동수단</option>
														</select>
													</div>
													<div class="col-2 form-group">
														<select
															class="second-category form-control form-control-sm"
															id="second-category" name="second-category">
															<option value="" selected>2차 분류</option>
														</select>
													</div>
													<div class="col-2 form-group">
														<select
															class="third-category form-control form-control-sm"
															id="third-category" name="third-category">
															<option value="" selected>3차 분류</option>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<th>진열 상태</th>
												<td class="row display">
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-display"
																id="check-display" name="display" required
																onclick="checkOnlyOne(this)" checked /> <label
																class="custom-control-label" for="check-display">진열함</label>
														</div>
													</div>
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-not-display"
																id="check-not-display" name="display" required
																onclick="checkOnlyOne(this)" /> <label
																class="custom-control-label" for="check-not-display">진열
																안함</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>신상품 유무</th>
												<td class="row is_new">
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-new" id="check-new"
																name="new" required onclick="checkOnlyOne(this)" checked />
															<label class="custom-control-label" for="check-new">유</label>
														</div>
													</div>
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-not-new"
																id="check-not-new" name="new" required
																onclick="checkOnlyOne(this)" /> <label
																class="custom-control-label" for="check-not-new">무</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>이벤트 적용</th>
												<td class="row event-row is_event">
													<div class="col-2 event-box">
														<select class="form-control form-control-sm event">
															<jsp:useBean id="now" class="java.util.Date" />
															<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"
																var="today" />
															<c:forEach var="event" items="${eventList}">
																<c:if test="${event.event_no == 0}">
																	<option value="${event.event_no}">${event.event_name}</option>
																</c:if>
																<c:if test="${event.event_end > today}">
																	<option value="${event.event_no}">${event.event_name}</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<th>자동 발주</th>
												<td class="row is_auto_order">
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-auto" id="check-auto"
																name="auto" required onclick="checkOnlyOne(this)"
																checked /> <label class="custom-control-label"
																for="check-auto">자동 발주 O</label>
														</div>
													</div>
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input check-not-auto"
																id="check-not-auto" name="auto" required
																onclick="checkOnlyOne(this)" /> <label
																class="custom-control-label" for="check-not-auto">자동
																발주 X</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th class="col-2">부품 개수</th>
												<td class="row">
													<div class="col-2 form-group">
														<input type="number"
															class="form-control-sm form-control parts_quantity"
															id="parts_quantity" name="parts_quantity" min="0" />
													</div> 개
													<div class="col-3 font-weight-bold text-center">권장 연령
													</div>
													<div class="col-2 form-group">
														<input type="number"
															class="d-inline form-control-sm form-control recommend_age"
															id="recommend_age" name="recommend_age" min="0" />
													</div> +
												</td>
											</tr>
											<tr>
												<th>가격</th>
												<td class="row">
													<div class="col-2 form-group">
														<input type="number"
															class="d-inline form-control-sm form-control product_price"
															id="product_price" name="product_price" min="0" />
													</div> ₩
												</td>
											</tr>
											<tr>
												<th>재고</th>
												<td class="row">
													<div class="col-2 form-group">
														<input type="number"
															class="d-inline form-control-sm form-control stock_quantity"
															id="stock_quantity" name="stock_quantity" min="0" />
													</div> 개
													<div class="col-3 font-weight-bold text-center">안전 재고
													</div>
													<div class="col-2 form-group">
														<input type="number"
															class="d-inline form-control-sm form-control safety_stock_quantity"
															id="safety_stock_quantity" name="safety_stock_quantity"
															min="0" />
													</div> 개
												</td>
											</tr>
											<tr>
												<th>상품 사진 등록</th>
												<td class="row flex-wrap">
													<div class="col-12">
														<div class="upload-area form-group">
															<span>여기를 클릭하거나 이미지를 드래그 해주세요.</span> <input type="file"
																class="uploadImg" id="input-multiple-image"
																name="input-multiple-image" accept="image/*" multiple />
														</div>
													</div>
													<div class="row col-12" id="multiple-container"></div>
												</td>
											</tr>
											<tr>
												<th>상품 간략 설명</th>
												<td class="row">
													<div class="col-8 form-group">
														<textarea class="form-control product_short_description"
															id="product_short_description"
															name="product_short_description" rows="4" id="comment"></textarea>
													</div>
												</td>
											</tr>
											<tr>
												<th>상품 상세 설명</th>
												<td class="row">
													<div class="col-8 form-group">
														<textarea class="form-control product_description"
															id="product_description" name="product_description"
															rows="4" id="comment"></textarea>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="d-flex justify-content-center">
									<button type="submit" class="col-1 btn btn-primary btn-sm mr-2">등록</button>
									<button type="button"
										class="col-1 btn btn-outline-light btn-sm"
										onclick="resetCondition()">초기화</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--**********************************
        Scripts
    ***********************************-->
	<!-- Daterangepicker -->
	<!-- momment js is must -->
	<script
		src="${contextPath}/resources/admin/vendor/moment/moment.min.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>

	<!-- Daterangepicker -->
	<script
		src="${contextPath}/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
	<script
		src="${contextPath}/resources/admin/js/plugins-init/sweetalert.init.js"></script>

	<!-- Jquery Validation -->
	<script
		src="${contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script
		src="${contextPath}/resources/admin/js/sjy/jquery.validate-init.js"></script>

	<script type="text/javascript">
        let categoryList = [];
        let img_files = [];
        let images = [];
        
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

        function checkOnlyOne(element) {
          const name = element.name;
          const checkboxes = document.getElementsByName(name);
          checkboxes.forEach((cb) => {
            cb.checked = false;
          });
          element.checked = true;
        }

        window.onload = function () {
          getCategory();
          displayCategory('.search-category');
          
          
			window.addEventListener('dragenter dragover', (e) => {
				e.stopPropagation();
				e.preventDefault();
				}, false);
          
          $('.uploadImg').on('change', handleImgFileSelect);
          $('.uploadImg').on('drop', function(){
        	  handleImgFileSelect;
        	  $('#multiple-container').empty();
          });
          
          $.validator.addMethod("imgValidation", function() {
	       	   if($('.product-img').length) {
	       		   return true;
	       	   } else {
	       		   return false;
	       	   }
	       	}, "상품 사진을 등록해주세요."); 
        };
        
        function handleImgFileSelect(e) {
        	  img_files = [];
        	  $('#multiple-container').empty();

        	  let files = e.target.files;
        	  let filesArr = Array.prototype.slice.call(files);

        	  let index = 0;
        	  filesArr.forEach(function (f) {
        	    if (!f.type.match('image.*')) {
        	      alert('이미지 파일만 업로드할 수 있습니다.');
        	      return;
        	    }

        	    img_files.push(f);

        	    let reader = new FileReader();
        	    reader.onload = function (e) {
        	      let html = `<div id="img_id_\${index}" data-file="\${f.name}">
        	      				<a href="javascript:void(0)" onclick="deleteImageAction(\${index})">
        	      					✖
        	      				</a>
        	      				<img src="\${e.target.result}" data-file="\${f.name}" class="product-img">
        	      			  </div>`;
        	      $('#multiple-container').append(html);
        	      images.push(e.target.result);
        	      index++;
        	    };
        	    reader.readAsDataURL(f);
        	  });
        }
        
        function deleteImageAction(index) {
	        img_files.splice(index, 1);
	
	        let img_id = `#img_id_\${index}`;
	        $(img_id).remove();
        }
        

      	function addProduct() {
	       let min_date = '';
	       let max_date = '';
           let display = 'N';
           let is_new = 'N';
           let is_auto_order = 'N';
           let categoryNo = 0;
          if (
            $('select.first-category').val() == '' ||
            $('select.second-category').val() == ''
          ) {
            categoryNo = $('select.first-category').val();
          } else if (
            $('select.second-category').val() != '' &&
            $('select.third-category').val() == ''
          ) {
            categoryNo = $('select.second-category').val();
          } else {
            categoryNo = $('select.third-category').val();
          }

          if ($('input.check-display').is(':checked')) {
            display = 'Y';
          }
          if ($('input.check-new').is(':checked')) {
            is_new = 'Y';
          }
          if ($('input.check-auto').is(':checked')) {
              is_auto_order = 'Y';
            }

          let product = {
            product_code: $('input.product_code').val(),
            product_category_no: categoryNo,
            product_name: $('input.product_name').val(),
            product_price: $('input.product_price').val(),
            product_description: $('textarea.product_description').val(),
            parts_quantity: $('input.parts_quantity').val(),
            stock_quantity: $('input.stock_quantity').val(),
            safety_stock_quantity: $('input.safety_stock_quantity').val(),
            recommend_age: $('input.recommend_age').val() + '+',
            product_main_image : images[0],
            product_short_description: $('textarea.product_short_description').val(),
            display: display,
            is_new: is_new,
            event_no: $("select.event option:selected").val(),
            is_auto_order : is_auto_order
          };
			
			let data = {product, images}
			
			$.ajax({
                url: `/admin/product/addProduct`,
                type: 'post',
                data: JSON.stringify(data),
                async: false,
                contentType: 'application/json',
                success: function (data) {
                  	addProductSuccess();     
                },
                error: function (request, status, error) {
                  addProductFail();
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
      	
        function addProductSuccess() {
          Swal.fire('상품이 저장되었습니다.', '', 'success').then(() => {
        	 location.reload();})
        }
        
        function addProductFail() {
            Swal.fire('상품 저장에 실패했습니다.', '', 'error');
          }

        function displayCategory(e) {
          $(`\${e} .first-category`).on('change', function () {
            let output = `<option selected value="">2차 분류</option>`;
            if ($(`\${e} .first-category option:selected`).val() == 1) {
              $(categoryList).each(function (index, item) {
                if (item.category_level == 'Medium') {
                  output += `<option value="\${item.product_category_no}">\${item.product_category_name}</option>`;
                }
              });
            }

            $('select.third-category').html(`<option selected>3차 분류</option>`);
            $('select.second-category').html(output);
            $('select.second-category').selectpicker('refresh');
            $('select.third-category').selectpicker('refresh');
          });

          $(`\${e} .second-category`).on('change', function () {
            let output = `<option selected value="">3차 분류</option>`;
            let selected = $(`\${e} .second-category option:selected`).val();
            $(categoryList).each(function (index, item) {
              if (
                selected == item.upper_category_no &&
                item.category_level == 'Small'
              ) {
                output += `<option value="\${item.product_category_no}">\${item.product_category_name}</option>`;
              }
            });
            $('select.third-category').html(output);
            $('select.third-category').selectpicker('refresh');
          });
        }

        function resetCondition() {
          img_files = [];
          $('#multiple-container').empty();
          $('.add-table input').val('');
          $('textarea').val('');
          $('select.search-word').val('product_code').prop('selected', true);
          $('select.first-category').val('').prop('selected', true);
          $('select.second-category').html(
            `<option value="" selected>2차 분류</option>`
          );
          $('select.third-category').html(
            `<option value="" selected>3차 분류</option>`
          );
          displayCategory('.search-category');
          $('select.date').val('register-date').prop('selected', true);
          $('.add-table input').prop('checked', false);
          $('select.stock').val('stock').prop('selected', true);
          $('select').selectpicker('refresh');
        }
    </script>
</body>
</html>
