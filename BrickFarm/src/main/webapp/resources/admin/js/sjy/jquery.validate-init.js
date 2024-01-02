jQuery(".form-valide").validate({
    rules: {
        "product_code": {
            required: !0,
            minlength: 5,
            digits: !0
        },
        "product_name": {
            required: !0,
            minlength: 3,
        },
        "first-category": {
            required: !0,
        },
        "second-category": {
            required: !0,
        },
        "third-category": {
            required: !0,
        },
        "parts_quantity": {
            required: !0,
            digits: !0,
            min: 10
        },
        "product_price": {
            required: !0,
            digits: !0,
            min: 1000
        },
        "recommend_age": {
            required: !0,
            digits: !0,
            min: 1
        },
        "stock_quantity": {
            required: !0,
            digits: !0,
            min : 10
        },
        "safety_stock_quantity": {
            required: !0,
            digits: !0,
            min : 50
        },
        "input-multiple-image": {
            imgValidation: true
        },
        "product_short_description": {
            required: !0,
            minlength: 10,
        },
        "product_description": {
            required: !0,
            minlength: 20,
        },
    },
    messages: {
        "product_code": {
            required: "상품 코드를 입력해주세요.",
            minlength: "상품코드는 5자 이상이어야 합니다.",
            digits : "숫자를 입력해주세요."
        },
        "product_name": {
        	required: "상품명을 입력해주세요.",
            minlength: "상품명은 5자 이상이어야 합니다.",
        },
        "first-category": "1차 분류를 선택해주세요.",
        "second-category": "2차 분류를 선택해주세요.",
        "third-category": "3차 분류를 선택해주세요.",
        "parts_quantity": {
            required: "부품 개수를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "10개 이상을 입력해주세요."
        },
        "recommend_age": {
            required: "권장 연령을 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "1살 이상을 입력해주세요."
        },
        "product_price": {
            required: "가격을 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "1000원 이상을 입력해주세요."
        },
        "safety_stock_quantity": {
            required: "안전 재고를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "50개 이상을 입력해주세요."
        },
        "stock_quantity": {
            required: "재고를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "10개 이상을 입력해주세요."
        },
        "input-multiple-image": {
            required: "상품 사진을 등록해주세요.",
        },
        "product_short_description": {
            required: "상품 간략 설명을 입력해주세요.",
            minlength: "10자 이상 입력해주세요.",
        },
        "product_description": {
            required: "상품 상세 설명을 입력해주세요.",
            minlength: "20자 이상 입력해주세요.",
        },
    },

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
     submitHandler: function(form) { 				
    	addProduct()			
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	addProductFail();				
    	} 				
    }
});

jQuery(".result-form-valide").validate({

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	addPlaceOrder()			
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('발주 등록 ');				
    	} 				
    }
});

jQuery(".modify-order-form-valide").validate({

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	modifyPlaceOrder()			
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('발주 수정 ');				
    	} 				
    }
});

jQuery(".modify-receiving-form-valide").validate({

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	modifyReceiving()			
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('입고 수정 ');				
    	} 				
    }
});

jQuery(".modify-carrying-out-form-valide").validate({

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	modifyCarryingOut()			
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('입고 수정 ');				
    	} 				
    }
});

jQuery(".event-form-valide").validate({
	rules: {
        "event_name": {
            required: !0,
            minlength: 5,
        },
        "event-date": {
            required: !0,
        },
        "discount_rate": {
            required: !0,
            number: !0,
            min: 5
        },
    },
    messages: {
        "event_name": {
            required: "이벤트 이름을 입력해주세요.",
            minlength: "이벤트 이름은 5자 이상이어야 합니다.",
        },
        "event-date": {
        	required: "이벤트 기간을 입력해주세요.",
        },
        "discount_rate": {
            required: "할인율을 입력해주세요.",
            number: "숫자를 입력해주세요.",
            min: "할인율은 5% 이상이어야 합니다."
        },
    },

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	addEvent()		
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('이벤트 등록 ');				
    	} 				
    }
});

jQuery(".modify-event-form-valide").validate({
	rules: {
        "event_name": {
            required: !0,
            minlength: 5,
        },
        "discount_rate": {
            required: !0,
            number: !0,
            min: 5
        },
    },
    messages: {
        "event_name": {
            required: "이벤트 이름을 입력해주세요.",
            minlength: "이벤트 이름은 5자 이상이어야 합니다.",
        },
        "discount_rate": {
            required: "할인율을 입력해주세요.",
            number: "숫자를 입력해주세요.",
            min: "할인율은 5% 이상이어야 합니다."
        },
    },

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
    submitHandler: function(form) { 				
    	modifyEvent()		
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	failAlert('이벤트 수정 ');				
    	} 				
    }
});

jQuery(".modify-product-valide").validate({
    rules: {
        "product_name": {
            required: !0,
            minlength: 3,
        },
        "first-category": {
            required: !0,
        },
        "second-category": {
            required: !0,
        },
        "third-category": {
            required: !0,
        },
        "parts_quantity": {
            required: !0,
            digits: !0,
            min: 10
        },
        "product_price": {
            required: !0,
            digits: !0,
            min: 1000
        },
        "recommend_age": {
            required: !0,
            digits: !0,
            min: 1
        },
        "stock_quantity": {
            required: !0,
            digits: !0,
            min : 10
        },
        "safety_stock_quantity": {
            required: !0,
            digits: !0,
            min : 50
        },
        "input-multiple-image": {
            imgValidation: true
        },
        "product_short_description": {
            required: !0,
            minlength: 10,
        },
        "product_description": {
            required: !0,
            minlength: 20,
        },
    },
    messages: {
        "product_name": {
        	required: "상품명을 입력해주세요.",
            minlength: "상품명은 5자 이상이어야 합니다.",
        },
        "first-category": "1차 분류를 선택해주세요.",
        "second-category": "2차 분류를 선택해주세요.",
        "third-category": "3차 분류를 선택해주세요.",
        "parts_quantity": {
            required: "부품 개수를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "10개 이상을 입력해주세요."
        },
        "recommend_age": {
            required: "권장 연령을 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "1살 이상을 입력해주세요."
        },
        "product_price": {
            required: "가격을 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "1000원 이상을 입력해주세요."
        },
        "safety_stock_quantity": {
            required: "안전 재고를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "50개 이상을 입력해주세요."
        },
        "stock_quantity": {
            required: "재고를 입력해주세요.",
            digits: "숫자를 입력해주세요.",
            min: "10개 이상을 입력해주세요."
        },
        "input-multiple-image": {
            required: "상품 사진을 등록해주세요.",
        },
        "product_short_description": {
            required: "상품 간략 설명을 입력해주세요.",
            minlength: "10자 이상 입력해주세요.",
        },
        "product_description": {
            required: "상품 상세 설명을 입력해주세요.",
            minlength: "20자 이상 입력해주세요.",
        },
    },

    ignore: [],
    errorClass: "invalid-feedback animated fadeInUp",
    errorElement: "div",
    errorPlacement: function(e, a) {
        jQuery(a).parents(".form-group").append(e)
    },
    highlight: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
    },
    success: function(e) {
        jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
    },
     submitHandler: function(form) { 				
    	modifyProduct()		
    },				
    invalidHandler: function(form, validator){ 					
    	var errors = validator.numberOfInvalids();					
    	if (errors) {						
    	 modifyProductFail();				
    	} 				
    }
});


