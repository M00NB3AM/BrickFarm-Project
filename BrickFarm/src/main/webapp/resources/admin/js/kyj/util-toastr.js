// 성공 토스트 메시지 표시
function toastrSuccess(title, message) {
  toastr.success(message, title, {
    positionClass: 'toast-top-full-width',
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: '300',
    hideDuration: '1000',
    extendedTimeOut: '1000',
    showEasing: 'swing',
    hideEasing: 'linear',
    showMethod: 'fadeIn',
    hideMethod: 'fadeOut',
    tapToDismiss: !1,
  });
}

// 경고 토스트 메시지 표시
function toastrWarning(title, message) {
  toastr.warning(message, title, {
    positionClass: 'toast-top-full-width',
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: '300',
    hideDuration: '1000',
    extendedTimeOut: '1000',
    showEasing: 'swing',
    hideEasing: 'linear',
    showMethod: 'fadeIn',
    hideMethod: 'fadeOut',
    tapToDismiss: !1,
  });
}

// 오류 토스트 메시지 표시
function toastrError(title, message) {
  toastr.error(message, title, {
    positionClass: 'toast-top-full-width',
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: '300',
    hideDuration: '1000',
    extendedTimeOut: '1000',
    showEasing: 'swing',
    hideEasing: 'linear',
    showMethod: 'fadeIn',
    hideMethod: 'fadeOut',
    tapToDismiss: !1,
  });
}