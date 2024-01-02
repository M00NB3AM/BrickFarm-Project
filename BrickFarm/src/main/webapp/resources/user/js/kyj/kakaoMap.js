const latGoott = 37.482041;
const lngGoott = 126.898203;

var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
var options = {
  //지도를 생성할 때 필요한 기본 옵션
  center: new kakao.maps.LatLng(latGoott, lngGoott), //지도의 중심좌표.
  level: 3, //지도의 레벨(확대, 축소 정도)
};

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 맵타입 컨트롤러 (스카이뷰 보기)
// var mapTypeControl = new kakao.maps.MapTypeControl();
// map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 줌 컨트롤러
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 마커 생성
var imageSrc = '/resources/user/images/kyj/location-dot-solid.svg'; // 마커이미지의 주소입니다
var imageSize = new kakao.maps.Size(30, 30);
var imageOption = { offset: new kakao.maps.Point(15, 30) };
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
var markerPosition = new kakao.maps.LatLng(latGoott, lngGoott);

var marker = new kakao.maps.Marker({
  position: markerPosition,
  image: markerImage,
});

marker.setMap(map);

// 커스텀 오버레이 내용 및 위치
var content = `<div class="map-info"><span>구트아카데미</span><br><a href="https://map.kakao.com/link/map/Goott,${latGoott},${lngGoott}" target="_blank">카카오 지도 보기</a></div>`;
var position = new kakao.maps.LatLng(latGoott, lngGoott);

// 커스텀 오버레이 생성
var customOverlay = new kakao.maps.CustomOverlay({
  clickable: true,
  position: position,
  content: content,
  yAnchor: 1.5,
  zIndex: 3,
});

customOverlay.setMap(map);
