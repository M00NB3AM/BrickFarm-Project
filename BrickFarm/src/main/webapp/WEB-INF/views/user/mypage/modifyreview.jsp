<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<meta charset="UTF-8" />
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<title>Brick Farm</title>
<!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<style type="text/css">
.a{
padding: 5px;
}
.area-file-upload {
        border: 2px dashed #cccccc; 
        padding: 20px; 
        text-align: center; 
        cursor: pointer; 
        width: 100%;
        height: 100px;
        &:hover {
		border: 2px dashed #ff4500; 
}
 }
.area-file-uploaded {
	display: flex;
	padding: 15px;
	gap: 15px;
	border: 1px solid #eee;
	border-radius: 6px;
	overflow-x: auto;
	
	-webkit-user-select:none;
	-moz-user-select:none;
	-ms-user-select:none;
	user-select:none;
}
.wrapper-img {
	position: relative;
}

.wrapper-img img.img-clickable {
	cursor: pointer;
}
.wrapper-img img.img-clickable:hover {
	opacity: 0.8;
}

.wrapper-img img.img-uploaded-file {
	width: 70px;
}
 
.icon {
	position: absolute;
	cursor: pointer;
	width: 20px;
	top: 0;
	right: 0;
	border: 1px solid #fff;
	border-radius: 50%;
}  
.icon:hover {
	filter: saturate(400%);
}
form input::file-selector-button {
        display: none;
}
</style>

<script type="text/javascript">
let fileInput;
let fileList;
$(function () {
	const reviewImgSize = "${productReviewImgs.size()}"
	const reviewImg = "${productReviewImgs}"
	fileInput = $("input[name='uploadFileArea']");
	 fileList = fileInput[0].files;
	 console.log(fileList)
	if(reviewImgSize > 0) {
		  $("#uploadFilesParent").show();
	}
	// 파일 업로드
	$("input[type='file']").on("change", function(event){
		
		 fileInput = $("input[name='uploadFileArea']");
		 fileList = fileInput[0].files;
		for(let i = 0 ; i < fileList.length ; i++) {
			let formData = new FormData();
			
			formData.append("uploadFiles", fileList[i]);
			 $.ajax({
			 url: "/mypage/uploadfile",
             type: "post",
             data: formData,
             dataType: "json",
             async: false,
             processData: false,
             contentType: false,
             success : function (data) {
				console.log("ajax통신 후 : " + data);
				showUploadedFile(data.fileList);
			}
		  });
		}
	});
	
	
});
	
//업로드 이미지 썸네일 출력
function showUploadedFile(data) {
    let output = "";

    $.each(data, function (i, e) {
      let fileName = e.new_file_name.replaceAll("\\", "/");
      console.log(e.fileName)
      console.log(e.thumb_file_name)
      if (e.thumb_file_name != null) {
        let thumb = e.thumb_file_name.replaceAll("\\", "/");
        console.log(thumb)
        output += `<div class="wrapper-img">`;
        output += `<img src="/resources/user/uploads/review\${thumb}" data-detail-src="/resources/user/uploads/review\${fileName}" id="\${e.new_file_name}" name="\${e.original_file_name}"  class="img-uploaded-file img-clickable" onclick="viewDetailImg('/resources/user/uploads/review\${fileName}')" />`;
      } else {
        output += `<a href="/resources/user/uploads/review\${fileName}" id="\${e.new_file_name}">\${e.original_file_name}</a>`;
      }
      output += `<img src="/resources/user/images/kyj/remove.png" id="${file.lastModified}" class="icon"  onclick="removeFile(this);" />`
      output += `</div>`;
    });

    $("#uploadFiles").html(output);
    $("#uploadFilesParent").show();
  }
function removeFile(event) {
	let removeFile = $(event).prev().attr("id");
	let removeName = $(event).prev().attr("name");
	//let product_review_no = $("input[name='product_review_no']").val();
	console.log(removeFile)
	fileInput = $("input[name='uploadFileArea']");
	fileList = fileInput[0].files;
	const dataTransfer = new DataTransfer();
	$.ajax({
		url: "/mypage/removefile",
		type: "get",
		data: { removeFile},
		dataType: "text",
		async: false,
		success: function (data) {
			console.log(data)
			if(data == "success") {
				Array.from(fileList)
				.filter(file => file.name != removeName)
				.forEach(file => {
					dataTransfer.items.add(file);
				});
				
				document.querySelector('#uploadFileArea').files = dataTransfer.files;
				
				$(event).parent().remove();
				if($(".img-uploaded-file").length == 0) {
					$("#uploadFilesParent").hide();
				}
			}
		},
	});
}
window.addEventListener("beforeunload", function (event) {
		fileInput = $("input[name='uploadFileArea']");
		fileList = fileInput[0].files;
	    $.ajax({
	           type: 'POST',
	           url: '/mypage/deleteAll',
	           data: {
	        	   
	           },
	           success: function(response) {
	               console.log('데이터 전송 성공: ' + response);
	           },
	           error: function() {
	               console.error('데이터 전송 실패');
	           },  
	       });
	});
	
</script>
</head>

<body class="config">
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>
	
	
<!--====== App Content ======-->
        <div class="app-content">

            <!--====== Section 1 ======-->
            <div class="u-s-p-y-90">
				<!--====== Section Content ======-->
				<div class="section__content">
					<div class="container">
						<div class="breadcrumb">
							<div class="breadcrumb__wrap">
								<ul class="breadcrumb__list">
									<li class="has-separator"><a href="/">Home</a></li>
									 <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
									<li class="is-marked"><a>리뷰 수정</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--====== Section Content ======-->
                <!--====== Detail Post ======-->
                <div class="detail-post">
                <div class="bp-detail">
                <div>
                <h1 class="section__heading u-c-secondary u-s-m-b-12" >리뷰수정 <span style="float: right; font-size: 15px; color: #7f7f7f; margin-top : 10px">작성일 : ${productReview.created_date}</span></h1>
          		</div>
                <hr style="height : 0.5px; background-color: black">
                <div class="col-lg-12 col-md-12 col-sm-12 u-s-m-b-30">
                <ul class="blog-w__list" style="list-style-type: disc; font-size: 14px; margin-left: 14px">
                <li>브릭팜의 후기는 일반 후기, 상품 사진 후기로 구성되어 있으며, 각각의 후기 작성 시 기준에 맞는 적립금이 지급됩니다.</li>
                <li>작성하신 후기는 브릭팜 및 브릭팜 글로벌 이용자에게 공개됩니다. 댓글은 브릭팜에서 확인하지 않습니다.</li>
                <li>일반 후기 작성 시 500원, 포토 리뷰 작성 시 1000원의 적립금을 평일 기준 2일 전후로 지급됩니다.</li>
                <li>후기 작성은 구매한 상품에 한하여 1회 작성 가능합니다.</li>
                <li>후기 작성 시 포인트는 최초 작성유형(일반 리뷰/포토 리뷰) 기준으로 지급 됩니다.</li>
                <li>후기 내용은 5자 이상 작성합니다. 상품과 관련 없는 내용, 단순 문자 및 기호의 나열/반복 확인 시 적립금 회수 및 지급 혜택이 제한됩니다.</li>
                <li>아래에 해당할 경우 적립금 지급이 보류되며, 이미 지급받으셨더라도 2차 검수를 통해 적립금을 회수할 수 있습니다. 또한 일부 후기는 조건에 따라 비노출 처리 됩니다.
                <br>
					- 문자 및 기호의 단순 나열, 반복된 내용의 후기
				   <br>
					- 주문하신 상품과 관련 없는 내용의 후기
					   <br>
					- 개인정보 및 광고, 비속어가 포함된 내용의 후기
					</li>
                </ul>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 u-s-m-b-30">
                <div class="table-p__box">
                <div class="table-p__img-wrap"><img class="u-img-fluid" src="${productReviewInfo.product_main_image }" alt=""></div>
                 <div class="table-p__info">
				 <span class="table-p__name"><a href="#">${productReviewInfo.product_name }</a></span>
				 <span class="table-p__category"><a href="#">${productReviewInfo.product_category_name }</a></span>
                      <ul class="table-p__variant-list">
                      <li><span>부품수: ${productReviewInfo.parts_quantity }</span></li>
                      <li><span>연령: ${productReviewInfo.recommend_age}</span></li>
                      </ul>
                  </div>
              	 </div>
              	 </div>
              	 <hr style="height: 1px; border: 0; background: #E2E2E2">
                                        <form class="pd-tab__rev-f2" action="/mypage/modifyreview" method="post">
                      					<div class="u-s-m-b-30">
                                                    <h4 class="u-s-m-b-15">별점을 매겨주세요</h4>
													<input type="hidden" name="detailed_order_no" value="${productReviewInfo.detailed_order_no}" />
													<input type="hidden" name="merchant_uid" value="${productReviewInfo.merchant_uid}" />
													<input type="hidden" name="product_code" value="${productReviewInfo.product_code}" />
													<input type="hidden" name="product_review_no" value="${productReview.product_review_no}" />
                                                    <div class="u-s-m-b-30">
                                                        <div class="rev-f2__table-wrap gl-scroll">
                                                            <table class="rev-f2__table">
                                                                <thead>
                                                                    <tr>
                                                                        <th>
                                                                            <div class="gl-rating-style-2"><i class="fas fa-star"></i>

                                                                                </div>
                                                                        </th>
                                                                        <th>
                                                                            <div class="gl-rating-style-2"><i class="fas fa-star"></i><i class="fas fa-star"></i>

                                                                                </div>
                                                                        </th>
                                                                        <th>
                                                                            <div class="gl-rating-style-2"><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>

                                                                                </div>
                                                                        </th>
                                                                        <th>
                                                                            <div class="gl-rating-style-2"><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>

                                                                                </div>
                                                                        </th>
                                                                        <th>
                                                                            <div class="gl-rating-style-2"><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>

                                                                                </div>
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr>
                                                                        <td>

                                                                            <!--====== Radio Box ======-->
                                                                            <div class="radio-box">

                                                                                <input type="radio" id="star-1" name="star_count" value="1">
                                                                                <div class="radio-box__state radio-box__state--primary">

                                                                                    <label class="radio-box__label" for="star-1"></label></div>
                                                                            </div>
                                                                            <!--====== End - Radio Box ======-->
                                                                        </td>
                                                                        <td>

                                                                            <!--====== Radio Box ======-->
                                                                            <div class="radio-box">

                                                                                <input type="radio" id="star-2" name="star_count" value="2">
                                                                                <div class="radio-box__state radio-box__state--primary">

                                                                                    <label class="radio-box__label" for="star-2"></label></div>
                                                                            </div>
                                                                            <!--====== End - Radio Box ======-->
                                                                        </td>
                                                                        <td>

                                                                            <!--====== Radio Box ======-->
                                                                            <div class="radio-box">

                                                                                <input type="radio" id="star-3" name="star_count" value="3" checked="checked">
                                                                                <div class="radio-box__state radio-box__state--primary">

                                                                                    <label class="radio-box__label" for="star-3"></label></div>
                                                                            </div>
                                                                            <!--====== End - Radio Box ======-->
                                                                        </td>
                                                                        <td>

                                                                            <!--====== Radio Box ======-->
                                                                            <div class="radio-box">

                                                                                <input type="radio" id="star-4" name="star_count" value="4">
                                                                                <div class="radio-box__state radio-box__state--primary">

                                                                                    <label class="radio-box__label" for="star-4"></label></div>
                                                                            </div>
                                                                            <!--====== End - Radio Box ======-->
                                                                        </td>
                                                                        <td>

                                                                            <!--====== Radio Box ======-->
                                                                            <div class="radio-box">

                                                                                <input type="radio" id="star-5" name="star_count" value="5">
                                                                                <div class="radio-box__state radio-box__state--primary">

                                                                                    <label class="radio-box__label" for="star-5"></label></div>
                                                                            </div>
                                                                            <!--====== End - Radio Box ======-->
                                                                        </td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <div class="rev-f2__group">
                                                        <div class="u-s-m-b-15">

                                                            <label class="gl-label" for="reviewer-text">내용*</label>
                                                            <textarea class="text-area text-area--primary-style" id="content" required="required" name="content" placeholder="다른 회원분에게 도움이 되는 나만의 팁을 소개해주세요. (5자 이상 500자 미만 작성)" minlength="5"  maxlength="500">${productReview.content }</textarea>
                                                            </div>
                                                   		</div>
                                                   		<!-- 이미지 업로드 영역 -->
                                                        <div class="u-s-m-b-15" id="">
								                         <label class="gl-label" for="uploadFileArea">업로드할 이미지 파일을 선택해주세요.</label>
														    <input type="file" id ="uploadFileArea" multiple class="area-file-upload" name="uploadFileArea" accept=".jpg, .png, .jpeg, .gif, .bmp">
														</div>
														
								                        </div>
								                        <!-- 이미지 업로드 영역 끝 -->
								                        
								                        <!-- 업로드 된 이미지 출력 영역 -->
								                         <div class="u-s-m-b-15" id="uploadFilesParent" style="display: none;">
								                          <label class="gl-label">첨부된 사진</label>
								                         <div class="area-file-uploaded" id="uploadFiles">
								                         <c:forEach var="reviewImg" items="${productReviewImgs}">
								                         <div class="wrapper-img">
								                         	 <img
								                                  src='/resources/user/uploads/review${reviewImg.thumb_file_name.replace("\\", "/")}'
								                                  data-detail-src="/resources/user/uploads/review${reviewImg.new_file_name.replace("\\\\", "/")}"
								                                  id="${reviewImg.new_file_name}"
								                                  class="img-uploaded-file img-clickable"
								                                />
								                                <img src="/resources/user/images/kyj/remove.png" class="icon" onclick="removeFile(this);" />
								                         </div>
								                         
								                         
								                         </c:forEach>
								                         </div>
								                        </div>
								                        <!-- 업로드 된 이미지 출력 영역 끝 -->
                                                    	<div class="u-s-m-b-15">
                                                    	<!--====== Check Box ======-->
                                                        <div class="check-box">
                                                        <input type="checkbox" name="review_agree" id="myReviewAgree" required="required">
                                                        <div class="check-box__state check-box__state--primary">
                                                        <label class="check-box__label" for="myReviewAgree">작성된 후기는 브릭팜 홍보 콘텐츠로 사용될 수 있습니다. (필수)</label>
                                                        </div>
                                                        </div>
                                                        <!--====== End - Check Box ======-->
                                                        </div>
                                                   		<div class="u-s-m-b-30" style="display: flex; justify-content: center;">
                                                        <button class="btn btn--e-transparent-brand-b-2" type="submit">수정</button>
                                                        </div>
                                                         </form>
                                              
                    </div>
                </div>
            </div>
            <!--====== End - Detail Post ======-->

            <!--====== End - Section 1 ======-->
        <!--====== End - App Content ======-->

	

			<!--====== Modal Section ======-->


			<!--====== End - Modal Section ======-->
		
		<!--====== End - Main App ======-->

		<!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
		<script>
			window.ga = function() {
				ga.q.push(arguments);
			};
			ga.q = [];
			ga.l = +new Date();
			ga("create", "UA-XXXXX-Y", "auto");
			ga("send", "pageview");
		</script>
		<script src="https://www.google-analytics.com/analytics.js" async
			defer></script>

	<!--====== Vendor Js ======-->
	<script src="/resources/user/js/vendor.js"></script>

	<!--====== jQuery Shopnav plugin ======-->
	<script src="/resources/user/js/jquery.shopnav.js"></script>

	<!--====== App ======-->
	<script src="/resources/user/js/app.js"></script>


		<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
