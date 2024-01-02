function closeToast() {
  $('.modal-msg').addClass('close-animation');
  setTimeout(function () {
    $('.modal-msg').hide();
  }, 450);
}
