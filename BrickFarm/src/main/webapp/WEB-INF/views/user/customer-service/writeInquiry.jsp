<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 문의작성</title>

    <!--====== Common Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== Inquiry Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/inquiry.css" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Common Js ======-->
    <script src="/resources/user/js/kyj/common.js"></script>

    <script>
      let FILE_LIST = [];
    
      $(function () {
    	/*
    	if (new URLSearchParams(document.location.search).has('status') && new URLSearchParams(document.location.search).get('status') == 'invalid') {
    	  alert("입력값이 유효하지 않습니다. 필수 항목을 모두 채워 다시 요청해주세요.");
    	}
    	*/
    	  
        $("#uploadFileArea").on("dragenter", function (evt) {
          evt.preventDefault();
          $(".area-file-upload").addClass("drag");
          $(".area-file-upload").text("놓아서 사진 업로드");
        });

        $("#uploadFileArea").on("dragleave", function (evt) {
          evt.preventDefault();
          $(".area-file-upload").removeClass("drag");
          $(".area-file-upload").text("업로드 할 사진을 이곳에 끌어다 놓아주세요");
        });

        $("#uploadFileArea").on("dragover", function (evt) {
          evt.preventDefault();
        });

        $("#uploadFileArea").on("drop", function (evt) {
          evt.preventDefault();
          $(".area-file-upload").removeClass("drag");
          $(".area-file-upload").text("업로드 할 사진을 이곳에 끌어다 놓아주세요");
          let fileList = evt.originalEvent.dataTransfer.files;
          // console.log("fileList", fileList);

          const availableExt = ["png", "jpg", "jpeg", "gif", "bmp"];

          for (let file of fileList) {
            let fileType = file.type.split("/")[0];
            let ext = file.type.split("/")[1];
            if (fileType != "image" && !availableExt.includes(ext)) {
              alert(`''\${file.name}' 은(는) 이미지 파일이 아닙니다.\n이미지 파일만 업로드 가능합니다.\n(png, jpg, jpeg, gif, bmp)`);
              return;
            }
          }

          for (let file of fileList) {
            let form = new FormData();
            
            let fileListJSONString = JSON.stringify(FILE_LIST);

            form.append("uploadFiles", file);
            form.append('fileListJSONString', fileListJSONString);
	
            $.ajax({
              url: "/customer-service/inquiry/uploadFile",
              type: "post",
              data: form,
              dataType: "json",
              async: false,
              processData: false,
              contentType: false,
              success: function (data) {
                console.log(`[\${this.url}] request success`);
                console.log(data);

                if (data.message == "success") {
                  showUploadedFile(data.fileList);
                  FILE_LIST = data.fileList;
                }
              },
              error: function (err) {
                console.log(`[\${this.url}] request error`);
                console.log(err);
              },
              complete: function () {
                // console.log(`[\${this.url}] request complete`);
              },
            });
          }
        });
      });

      function showUploadedFile(data) {
        let output = "";

        $.each(data, function (i, e) {
          let fileName = e.new_file_name.replaceAll("\\", "/");
          if (e.thumbnail_file_name != null) {
            let thumb = e.thumbnail_file_name.replaceAll("\\", "/");
            // 이미지
            output += `<div class="wrapper-img">`;
            output += `<img src="/resources/user/uploads/inquiry\${thumb}" data-detail-src="/resources/user/uploads/inquiry\${fileName}" id="\${e.new_file_name}" class="img-uploaded-file img-clickable" onclick="viewDetailImg('/resources/user/uploads/inquiry\${fileName}')" />`;
          } else {
            output += `<a href="/resources/user/uploads/inquiry\${fileName}" id="\${e.new_file_name}">\${e.original_file_name}</a>`;
          }
          output += `<img src="/resources/user/images/kyj/remove.png" class="icon" onclick="removeFile(this);" />`;
          output += `</div>`;
        });

        $("#uploadFiles").html(output);
        $("#uploadFilesParent").show();
      }

      function removeFile(evtElement) {
        let removeFile = $(evtElement).prev().attr("id");
        // console.log(removeFile);
        let fileListJSONString = JSON.stringify(FILE_LIST);

        $.ajax({
          url: "removeFile",
          type: "get",
          data: {
            removeFile,
            fileListJSONString,
          },
          dataType: "json",
          async: false,
          success: function (data) {
            console.log(`[\${this.url}] request success`);
            console.log(data);

            if (data.message == "success") {
        	  FILE_LIST = data.fileList;
              $(evtElement).parent().remove();
              if ($(".img-uploaded-file").length == 0) {
                $("#uploadFilesParent").hide();
              }
            }
          },
          error: function (err) {
            console.log(`[\${this.url}] request error`);
            console.log(err);
          },
          complete: function () {
            // console.log(`[\${this.url}] request complete`);
          },
        });
      }

      function btnCancel() {
    	let fileListJSONString = JSON.stringify(FILE_LIST);
    	
        $.ajax({
          url: "removeAllFile",
          type: "get",
          data: {
            fileListJSONString
          },
          dataType: "json",
          async: false,
          success: function (data) {
            // console.log(`[\${this.url}] request success`);
            // console.log(data);

            if (data == "success") {
              $("#uploadFilesParent").hide();
              location.href = "list";
            }
          },
          error: function (err) {
            // console.log(`[\${this.url}] request error`);
            // console.log(err);
          },
          complete: function () {
            // console.log(`[\${this.url}] request complete`);
          },
        });
      }

      function viewDetailImg(imgSrc, imgAlt) {
        // console.log(imgSrc, imgAlt);
        $("#imgModal").attr("src", imgSrc);
        $("#imgModal").attr("alt", imgAlt);
        $("#modal").modal();
      }

      function handlerPrevImg() {
        const images = $(".wrapper-img").children(".img-uploaded-file");
        const currentSrc = $("#imgModal").attr("src");

        let currentIndex;
        for (i = 0; i < images.length; i++) {
          // console.log($(images[i]).attr("data-detail-src"));
          // console.log(currentSrc);
          if ($(images[i]).attr("data-detail-src") == currentSrc) {
            currentIndex = i;
            break;
          }
        }

        // console.log(currentIndex);
        if (currentIndex > 0) {
          $("#imgModal").attr("src", $(images[currentIndex - 1]).attr("data-detail-src"));
        } else {
          $("#imgModal").attr("src", $(images[images.length - 1]).attr("data-detail-src"));
        }
      }

      function handlerNextImg() {
        const images = $(".wrapper-img").children(".img-uploaded-file");
        const currentSrc = $("#imgModal").attr("src");

        let currentIndex;
        for (i = 0; i < images.length; i++) {
          // console.log($(images[i]).attr("data-detail-src"));
          // console.log(currentSrc);
          if ($(images[i]).attr("data-detail-src") == currentSrc) {
            currentIndex = i;
            break;
          }
        }

        // console.log(currentIndex)
        if (currentIndex < images.length - 1) {
          $("#imgModal").attr("src", $(images[currentIndex + 1]).attr("data-detail-src"));
        } else {
          $("#imgModal").attr("src", $(images[0]).attr("data-detail-src"));
        }
      }
      
      function validation() {
    	  const elementTitle = $('input[name="title"]');
    	  // const inquiryCategoryNo = $('input[name="inquiryCategoryNo"]').val();
    	  const elementContent = $('textarea[name="content"]');
    	  
    	  let isValid = true;
    	  
    	  if (elementTitle.val().length < 1) {
    		alert("제목은 필수 입력 사항입니다.");
    		elementTitle.focus();
    		isValid = false;
    	  }
    	  
    	  if (elementContent.val().length < 1) {
    		alert("내용은 필수 입력 사항입니다.");
    		elementContent.focus();    		
    		isValid = false;
    	  }
    	  
    	  if(isValid) {
    		$("#fileListJSONString").val(JSON.stringify(FILE_LIST));
    	  	return true;
    	  } else{
    		return false;
    	  }
      }
    </script>
  </head>
  <body class="config">
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <c:choose>
   	  <c:when test='${param.status == "invalid"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">입력값이 유효하지 않습니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
   	  </c:when>
   	  <c:when test='${param.status == "insertFailed"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">작성 과정에서 에러가 발생했습니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
   	  </c:when>
      <c:when test='${param.status == "serverError"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">일시적인 서버 에러입니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
      </c:when>
    </c:choose>
    <div id="app">
      <!--====== App Content ======-->
      <div class="app-content">
        <!--====== Section 1 ======-->
        <div class="u-s-p-y-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb">
                <div class="breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <li class="has-separator">
                      <a href="/">홈</a>
                    </li>
                    <li class="has-separator">
                      <a href="/customer-service/inquiry/list">문의하기</a>
                    </li>
                    <li class="is-marked">
                      <a>문의작성</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 1 ======-->

        <!--====== Section 2 ======-->
        <div class="u-s-p-b-60">
          <!--====== Section Intro ======-->
          <div class="section__intro u-s-m-b-60">
            <div class="container">
              <div class="row">
                <div class="col-lg-12">
                  <div class="section__text-wrap">
                    <h1 class="section__heading u-c-secondary">문의하기</h1>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Intro ======-->

          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row row--center">
                <div class="col-lg-6 col-md-8 u-s-m-b-30">
                  <div class="l-f-o">
                    <div class="l-f-o__pad-box">
                      <form class="l-f-o__form" action="/customer-service/inquiry/write" method="post">
                      	<input type="hidden" name="fileListJSONString" id="fileListJSONString"/>
                        <div class="u-s-m-b-30">
                          <label class="gl-label" for="reg-fname">제목 *</label>
                          <input class="input-text input-text--primary-style" type="text" id="reg-fname" placeholder="제목을 입력해주세요." name="title" />
                        </div>
                        <div class="gl-inline">
                          <div class="u-s-m-b-30">
                            <!--====== Date of Birth Select-Box ======-->
                            <span class="gl-label">문의 분류 *</span>
                            <div class="gl-dob">
                              <select class="select-box select-box--primary-style" name="inquiryCategoryNo">
                                <c:forEach var="inquiryCategory" items="${inquiryCategoryList}">
                                  <c:choose>
                                    <c:when test="${inquiryCategory.inquiry_category_no == 1}">
                                      <option value="${inquiryCategory.inquiry_category_no}" selected>${inquiryCategory.inquiry_category_name}</option>
                                    </c:when>
                                    <c:otherwise>
                                      <option value="${inquiryCategory.inquiry_category_no}">${inquiryCategory.inquiry_category_name}</option>
                                    </c:otherwise>
                                  </c:choose>
                                </c:forEach>
                              </select>
                            </div>
                            <!--====== End - Date of Birth Select-Box ======-->
                          </div>
                        </div>
                        <div class="u-s-m-b-30">
                          <label class="gl-label" for="reg-fname">내용 *</label>
                          <textarea
                            class="input-text input-text--primary-style textarea-inquiry-content"
                            id="reg-fname"
                            placeholder="문의하실 내용을 입력해주세요."
                            name="content"
                          ></textarea>
                        </div>
                        <div class="u-s-m-b-30">
                          <label class="gl-label">사진 업로드</label>
                          <div class="area-file-upload" id="uploadFileArea">업로드 할 사진을 이곳에 끌어다 놓아주세요</div>
                        </div>
                        <div class="u-s-m-b-30" id="uploadFilesParent">
                          <label class="gl-label">첨부된 사진</label>
                          <div class="area-file-uploaded" id="uploadFiles"></div>
                        </div>
                        <div class="u-s-m-b-30">
                          <button class="btn btn--e-transparent-brand-b-2" type="submit" onclick="return validation();">문의하기</button>
                        </div>
                        <a class="gl-link" href="javascript:btnCancel();">돌아가기</a>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 2 ======-->
      </div>
    </div>
    <div class="modal fade" id="modal">
      <div class="modal-dialog modal-lg">
        <div class="wrapper-modal-img">
          <img class="icon-controller icon-prev-arrows" id="iconPrev" onclick="handlerPrevImg();" />
          <img id="imgModal" />
          <img class="icon-controller icon-next-arrows" id="iconNext" onclick="handlerNextImg();" />
        </div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
