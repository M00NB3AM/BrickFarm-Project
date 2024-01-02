// 윈도우.온로드
window.onload = function () {
        outputPrice();
      };

// 금액 폼변환
      function outputPrice() {
        let total_price = Number($('td[id=total_price_input]').html());
        let total_product_price = Number($('td[id=total_product_price_input]').html());
        let total_discount_price = Number($('td[id=total_discount_price_input]').html());
        let post_money = Number($('td[id=post_money_input]').html());
        let point_pay_money = Number($('td[id=point_pay_money_input]').html());
        let pay_money = Number($('td[id=pay_money_input]').html());

        let event_dc_price = total_price - total_product_price;

        $('#total_price_input').html(total_price.toLocaleString('ko-KR') + '원');
        $('#total_product_price_input').html('-' + event_dc_price.toLocaleString('ko-KR') + '원');
        $('#total_discount_price_input').html('-' + total_discount_price.toLocaleString('ko-KR') + '원');
        $('#post_money_input').html(post_money.toLocaleString('ko-KR') + '원');
        $('#point_pay_money_input').html('-' + point_pay_money.toLocaleString('ko-KR') + '원');
        $('#pay_money_input').html(pay_money.toLocaleString('ko-KR') + '원');
      }