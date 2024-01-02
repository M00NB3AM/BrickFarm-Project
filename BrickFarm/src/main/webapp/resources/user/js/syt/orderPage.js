// ---전역변수---
      let isGoPopup = false;
      let totalCount = 0;
// ---전역변수 끝---

//윈도우.온로드
      window.onload = function () {
      	
        // 상품 가격 출력하는 함수 호출
        outputPrice();

        // goPopup 온클릭 이벤트 (주소 API)
        $('#goPopup').on('click', function () {
          if (isGoPopup) {
            //goPopup();
            $('#billing-detaile-address').val('');
            findkakaoaddress();
          }
        });

        // isGoPopup 유무 바꾸기 (주소 API)
        $('#get-address').change(function () {
          if ($(this).is(':checked')) {
            isGoPopup = false;
          } else {
            isGoPopup = true;
          }
        });

        // make_address_checked 체크되면 make_address(hidden)의 벨류 변경
        $('#make_address_checked').change(function () {
          if ($(this).is(':checked')) {
            $('#get-address').attr('disabled', true);
            $('input[name=make_address]').val('on');
          } else {
            $('#get-address').attr('disabled', false);
            $('input[name=make_address]').val('off');
          }
        });

        // 결제 수단별 선택사항 클릭시 메세지 출력
        $('input[name=paymentType]').change(function () {
          if ($('input[name=paymentType]:checked').val() == 'vbank') {
            $('#create-cash-order').show();
          } else if ($('input[name=paymentType]:checked').val() == 'card') {
            $('#create-cash-order').hide();
            $('#refund_account').val('');
            $('#bank_brand').val('').prop('selected', true);
          }
        });
        
        // 쿠폰 선택 버튼
        $('#coupon-choces-btn').click(function () {
          chocesCoupon();
        });

        //포인트 사용 버튼(누를시 포인트량 예외처리)
        $('#point-choces-btn').click(function () {
          verifyAll();
        });

        // 포인트 모두 사용 체크
        $('input[type=checkbox][name=allUsePoint]').change(function () {
          let accrualAmount = $('#accrualAmount').text();

          if ($(this).is(':checked')) {
            $('#use_point').val(accrualAmount);
            
          } else {
            $('#use_point').val(0);
          }
          
        });
        
      };
// ------------------------------------윈도우.온로드 끝나는곳-----------------------------------------------------

// 카카오 주소 API 호출
      function findkakaoaddress() {
        new daum.Postcode({
          oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') {
              // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
            } else {
              // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
              // 법정동명이 있을 경우 추가한다. (법정리는 제외)
              // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
              if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraAddr += data.bname;
              }
              // 건물명이 있고, 공동주택일 경우 추가한다.
              if (data.buildingName !== '' && data.apartment === 'Y') {
                extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
              }
              // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
              if (extraAddr !== '') {
                extraAddr = ' (' + extraAddr + ')';
              }
              // 조합된 참고항목을 해당 필드에 넣는다.
            } else {
              document.getElementById('billing-address').value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            var address = addr + extraAddr;
            var inputElement = document.getElementById('billing-address');
            var currentreadonlyState = inputElement.readOnly; // readonly상태인지 확인
            if (inputElement) {
              inputElement.readOnly = !currentreadonlyState; //readonly 상태의 input태그를 !readonly로 바꿔주기.
            }
            document.getElementById('billing-zip').value = data.zonecode;
            document.getElementById('billing-address').value = address;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('billing-detaile-address').focus();
            inputElement.readOnly = !currentreadonlyState;
          },
        }).open();
      }
// --------------------------------------------주소 API 끝-------------------------------------------------------

//배송지 선택 함수
      function choiceAddress() {
        let recipient = $('input[type=radio][name=address]:checked').closest('tr').find('td[name=recipient]').text();
        let phone_number = $('input[type=radio][name=address]:checked').closest('tr').find('td[name=phone_number]').text();
        let zip_code = $('input[type=radio][name=address]:checked').closest('tr').find('td[name=zip_code]').text();
        let recipient_address = $('input[type=radio][name=address]:checked').closest('tr').find('td[name=address]').text();
        let recipient_detaile_address = $('input[type=radio][name=address]:checked').closest('tr').find('td[name=detaile_address]').text();

        $('input[name=recipient]').val(recipient);
        $('input[name=recipient_phone]').val(phone_number);
        $('input[name=recipient_zip_code]').val(zip_code);
        $('input[name=recipient_address]').val(recipient_address);
        $('input[name=recipient_detaile_address]').val(recipient_detaile_address);
      }
      
// 포인트 예외처리 함수
      function verifyAll() {
        let totalProductPrice =
          Number($('#total_evnet_product_price').val()) +
          Number($('input[name=post_money]').val()) -
          Number($('#total_discount_price').val());
        
        // 포인트 전부사용 체크후 쿠폰 변경시 자동 변경되는 로직 
        if($('input[type=checkbox][name=allUsePoint]').is(":checked")) {
            let accrualAmount = $('#accrualAmount').text();
          	$('#use_point').val(accrualAmount);
        }

        let usePoint = 0;

        if ($('#use_point').val() < 0) {
          usePoint = 0;
        } else {
          if ($('#use_point').val() > Number($('#accrualAmount').text())) {
            if (Number($('#accrualAmount').text()) > totalProductPrice) {
              usePoint = totalProductPrice;
            } else if (Number($('#accrualAmount').text()) <= totalProductPrice) {
              usePoint = Number($('#accrualAmount').text());
            }
          } else {
            if ($('#use_point').val() > totalProductPrice) {
              usePoint = totalProductPrice;
            } else if ($('#use_point').val() <= totalProductPrice) {
              usePoint = Number($('#use_point').val());
            }
          }
        }

        // 포인트 입력창 수치 변경
        $('#use_point').val(usePoint);
        //금액 포인트(히든)에 수치변경
        $('input[name=point_pay_money]').attr('value', usePoint);
        // 금액들 다시 출력
        outputPrice();
      }

// 사용쿠폰 알아내는 함수
      function chocesCoupon() {
        let coupon = $('#coupon-choces option:selected').val();
        if (coupon != '') {
          let coupon_held_no = coupon.split('&')[0];
          let discount_rate = coupon.split('&')[1];
          $('input[name=coupon_held_no]').attr('value', coupon_held_no);
          $('input[name=discount_rate]').attr('value', discount_rate);
			
		  // 각품목 개별 반올림 후 합산.
          let total_discount_price = 0;
          $('.w-r__price').each(function () {
            let price = Number(
              $(this).text().replace(/[^\d]+/g, '')
            );
            let quantity = Number($(this).prev().text().split('개', 1));
            total_discount_price += Math.round(price * quantity * discount_rate);
          });

          $('#total_discount_price').attr('value', total_discount_price);
 
          verifyAll();
          
        } else {
          $('input[name=coupon_held_no]').attr('value', -1);
          $('input[name=discount_rate]').attr('value', 0);
          $('#total_discount_price').attr('value', 0);
          verifyAll();
        }
      }

// 상품가격 계산후 출력 하는 함수
      function outputPrice() {
        let point_pay_money = Number($('input[name=point_pay_money]').val());

        // 상품 원가 계산
        let total_product_price = 0;
        $('.product_price').each(function () {
          let price = Number(
            $(this)
              .val()
              .replace(/[^\d]+/g, '')
          );
          let quantity = Number($(this).next().val());
          total_product_price += price * quantity;
        });

        //이벤트 가격 및 제품 총수량 계산
        let total_evnet_product_price = 0;
        let productCount = 0;
        $('.w-r__price').each(function () {
          let price = Number(
            $(this)
              .text()
              .replace(/[^\d]+/g, '')
          );
          let quantity = Number($(this).prev().text().split('개', 1));
          productCount += quantity;
          total_evnet_product_price += price * quantity;
        });
        totalCount = productCount;

        // 이벤트로 할인된가격
        let event_dc_price = total_product_price - total_evnet_product_price;

        let total_discount_price = Number($('#total_discount_price').val());

        // 배송비(post_money)
        let post_money = 0;
        if (total_evnet_product_price - total_discount_price < 50000) {
          post_money = 2500;
        }
        //총 결제 금액
        let pay_money = total_evnet_product_price - total_discount_price + post_money - point_pay_money;

        $('#total_product_price_input').html(total_product_price.toLocaleString('ko-KR') + '원');
        $('#total_evnet_product_price_input').html('-' + event_dc_price.toLocaleString('ko-KR') + '원');
        $('#total_evnet_product_price').attr('value', total_evnet_product_price);
        $('#total_discount_price_input').html('-' + total_discount_price.toLocaleString('ko-KR') + '원');
        $('#post_money_input').html(post_money.toLocaleString('ko-KR') + '원');
        $('input[name=post_money]').attr('value', post_money);
        $('#point_pay_money_input').html('-' + point_pay_money.toLocaleString('ko-KR') + '원');
        $('input[name=point_pay_money]').attr('value', point_pay_money);
        $('#pay_money_input').html(pay_money.toLocaleString('ko-KR') + '원');
        $('input[name=pay_money]').attr('value', pay_money);
      }
// ----------------------------------------------------------------------------------------------

// --------------------------결제 ------------------------------------------------
// 결제수단별 결제 호출함수 구분
      function clickRequestPay() {
    	let deliveryDataVerification = false;
    	// 이름 및 전화번호 입력 예외처리
        const patternPhone = /^(01[016789]{1})-?([0-9]{3,4})-?([0-9]{4})$/;
        // 결제 수단 가져오기
        let pay_method = $('input[name=paymentType]:checked').val();
        
        if($("#get-address").is(':checked') == false) {
        	if($("#billing-fname").val() == null || $("#billing-fname").val() == "") {
        		alert("이름을 입력해주세요.");
        		document.getElementById("billing-fname").focus();
        	} else if($("#billing-phone").val() == null || $("#billing-phone").val() == "") {
        		alert("핸드폰번호를 입력해주세요.");
        		document.getElementById("billing-phone").focus();
        	} else if(!patternPhone.test($("#billing-phone").val())){
        		alert("핸드폰번호를 정확하게 입력해주세요.");
        		document.getElementById("billing-phone").focus();
        	} else if($("#billing-detaile-address").val() == null || $("#billing-detaile-address").val() == "" ) {
        		alert("상세주소를 입력해주세요.");
        		document.getElementById("billing-detaile-address").focus();
        	} else {
        		let intPhone = $("#billing-phone").val().replace(/[^0-9]/g, '');
        		let phoneNumber =intPhone.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/\-{1,2}$/g, "");
        		$("#billing-phone").val(phoneNumber);
        		deliveryDataVerification = true; 		
        	}
        } else {
        	deliveryDataVerification = true; 
        }

        //0원 결제 예외 처리
        if($('input[name=pay_money]').val() != 0) {
	        if(deliveryDataVerification){
	          // 결제수단 선택 예외처리
	          if (pay_method != null) {
	            if (pay_method == 'vbank') {
	              // 환불계좌 입력 예외 처리
	              let bank_brand = $('#bank_brand').val();
	              let refund_account = $('#refund_account').val();
	              if (bank_brand != '' && refund_account != '') {
	                requestPay(pay_method);
	              } else {
	                alert('환불계좌를 입력하세요.');
	                document.getElementById("refund_account").focus();
	              }
	            } else {
	              requestPay(pay_method);
	            }
	          } else {
	            alert('결제방법을 선택하세요.');
	          }
	        }
        } else if($('input[name=pay_money]').val() == 0) {
        	requestPay("point");
        }
      }
      
// 결제 이름 만들기
      function createName() {
        let createName = '';
        if ($('.o-card__name:first-child').length > 1) {
          createName = $('.o-card__name:first-child').eq(0).text() + ' 외 ' + (totalCount - 1) + '개';
        } else {
          createName = $('.o-card__name:first-child').eq(0).text();
        }

        return createName;
      }

// 입금 말료 날짜 만들기
      function createVbankDue() {
        let today = new Date();
        let year = today.getFullYear();
        let month = ('0' + (today.getMonth() + 1)).slice(-2);
        let day = ('0' + (today.getDate() + 3)).slice(-2);
        let vbank_due = year + month + day;

        return vbank_due;
      }
// --------------------------결제 끝------------------------------------------------