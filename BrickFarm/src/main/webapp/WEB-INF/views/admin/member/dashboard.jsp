<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 대시보드</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
<!-- Datatable -->
<link
	href="${contextPath }/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<!-- Toastr -->
<link rel="stylesheet" href="/resources/admin/vendor/toastr/css/toastr.min.css" />
</head>
<style>
#indicator {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}
.listCoupon{
	padding: 0.55rem 0.8rem !important;
	font-size: 14px !important;
}


#first {
font-size: 20px;
font-weight: '700';
color : gold;
}

#second {
font-size: 17px;
font-weight: '700';
color : silver;
}

#third {
font-size: 15px;
font-weight: '700';
color : brown;
}

</style>
<body>
	<div id="preloader">
		<img id="indicator" src="${contextPath}/resources/admin/images/lego.gif" alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<!-- row -->
			<div class="container-fluid">
				<div class="form-head d-flex mb-3 align-items-start">
					<div class="mr-auto d-none d-lg-block">
						<h2 class="text-black font-w600 mb-0">Dashboard</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary">
										<svg width="36" height="28" viewBox="0 0 36 28" fill="none" xmlns="http://www.w3.org/2000/svg">
											<path d="M31.75 6.75H30.0064L30.2189 4.62238C30.2709 4.10111 30.2131 3.57473 30.0493 3.07716C29.8854 2.57959 29.6192 2.12186 29.2676 1.73348C28.9161 1.3451 28.4871 1.03468 28.0082 0.822231C27.5294 0.609781 27.0114 0.500013 26.4875 0.5H7.0125C6.48854 0.500041 5.9704 0.609864 5.49148 0.822391C5.01256 1.03492 4.58348 1.34543 4.23189 1.73392C3.88031 2.12241 3.61403 2.58026 3.45021 3.07795C3.28639 3.57564 3.22866 4.10214 3.28075 4.6235L5.2815 24.6224C5.31508 24.9222 5.38467 25.2168 5.48875 25.5H1.75C1.41848 25.5 1.10054 25.6317 0.866116 25.8661C0.631696 26.1005 0.5 26.4185 0.5 26.75C0.5 27.0815 0.631696 27.3995 0.866116 27.6339C1.10054 27.8683 1.41848 28 1.75 28H31.75C32.0815 28 32.3995 27.8683 32.6339 27.6339C32.8683 27.3995 33 27.0815 33 26.75C33 26.4185 32.8683 26.1005 32.6339 25.8661C32.3995 25.6317 32.0815 25.5 31.75 25.5H28.0115C28.1154 25.2172 28.1849 24.9229 28.2185 24.6235L28.881 18H31.75C32.7442 17.9989 33.6974 17.6035 34.4004 16.9004C35.1035 16.1974 35.4989 15.2442 35.5 14.25V10.5C35.4989 9.50577 35.1035 8.55258 34.4004 7.84956C33.6974 7.14653 32.7442 6.75109 31.75 6.75ZM9.0125 25.5C8.70243 25.501 8.40314 25.3862 8.17327 25.1782C7.9434 24.9701 7.79949 24.6836 7.76975 24.375L5.7685 4.37575C5.75109 4.20188 5.7703 4.0263 5.82488 3.86031C5.87946 3.69432 5.96821 3.5416 6.0854 3.412C6.2026 3.28239 6.34564 3.17877 6.50532 3.10781C6.665 3.03685 6.83777 3.00013 7.0125 3H26.4875C26.6622 3.00012 26.8349 3.03681 26.9945 3.10772C27.1541 3.17863 27.2972 3.28218 27.4143 3.4117C27.5315 3.54123 27.6203 3.69386 27.6749 3.85977C27.7295 4.02568 27.7488 4.20119 27.7315 4.375L25.7308 24.3762C25.7007 24.6848 25.5566 24.971 25.3267 25.1788C25.0967 25.3867 24.7975 25.5012 24.4875 25.5H9.0125ZM33 14.25C32.9998 14.5815 32.868 14.8993 32.6337 15.1337C32.3993 15.368 32.0815 15.4998 31.75 15.5H29.1311L29.7561 9.25H31.75C32.0815 9.2502 32.3993 9.38196 32.6337 9.61634C32.868 9.85071 32.9998 10.1685 33 10.5V14.25Z" fill="#2F4CDD" />
										</svg>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${dashboard.regist }</span>
										</h3>
										<p class="mb-0">신규 회원</p>
										<small>(오늘 기준)</small>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary">
										<svg width="20" height="36" viewBox="0 0 20 36" fill="none" xmlns="http://www.w3.org/2000/svg">
											<path d="M19.08 24.36C19.08 25.64 18.76 26.8667 18.12 28.04C17.48 29.1867 16.5333 30.1467 15.28 30.92C14.0533 31.6933 12.5733 32.1333 10.84 32.24V35.48H8.68V32.24C6.25333 32.0267 4.28 31.2533 2.76 29.92C1.24 28.56 0.466667 26.84 0.44 24.76H4.32C4.42667 25.88 4.84 26.8533 5.56 27.68C6.30667 28.5067 7.34667 29.0267 8.68 29.24V19.24C6.89333 18.7867 5.45333 18.32 4.36 17.84C3.26667 17.36 2.33333 16.6133 1.56 15.6C0.786667 14.5867 0.4 13.2267 0.4 11.52C0.4 9.36 1.14667 7.57333 2.64 6.16C4.16 4.74666 6.17333 3.96 8.68 3.8V0.479998H10.84V3.8C13.1067 3.98667 14.9333 4.72 16.32 6C17.7067 7.25333 18.5067 8.89333 18.72 10.92H14.84C14.7067 9.98667 14.2933 9.14667 13.6 8.4C12.9067 7.62667 11.9867 7.12 10.84 6.88V16.64C12.6 17.0933 14.0267 17.56 15.12 18.04C16.24 18.4933 17.1733 19.2267 17.92 20.24C18.6933 21.2533 19.08 22.6267 19.08 24.36ZM4.12 11.32C4.12 12.6267 4.50667 13.6267 5.28 14.32C6.05333 15.0133 7.18667 15.5867 8.68 16.04V6.8C7.29333 6.93333 6.18667 7.38667 5.36 8.16C4.53333 8.90667 4.12 9.96 4.12 11.32ZM10.84 29.28C12.28 29.12 13.4 28.6 14.2 27.72C15.0267 26.84 15.44 25.7867 15.44 24.56C15.44 23.2533 15.04 22.2533 14.24 21.56C13.44 20.84 12.3067 20.2667 10.84 19.84V29.28Z" fill="#2F4CDD" />
										</svg>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${dashboard.login}</span>
										</h3>
										<p class="mb-0">방문회원</p>
										<small>(오늘 기준)</small>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary">
										<svg width="32" height="31" viewBox="0 0 32 31" fill="none" xmlns="http://www.w3.org/2000/svg">
											<path d="M4 30.5H22.75C23.7442 30.4989 24.6974 30.1035 25.4004 29.4004C26.1035 28.6974 26.4989 27.7442 26.5 26.75V16.75C26.5 16.4185 26.3683 16.1005 26.1339 15.8661C25.8995 15.6317 25.5815 15.5 25.25 15.5C24.9185 15.5 24.6005 15.6317 24.3661 15.8661C24.1317 16.1005 24 16.4185 24 16.75V26.75C23.9997 27.0814 23.8679 27.3992 23.6336 27.6336C23.3992 27.8679 23.0814 27.9997 22.75 28H4C3.66857 27.9997 3.3508 27.8679 3.11645 27.6336C2.88209 27.3992 2.7503 27.0814 2.75 26.75V9.25C2.7503 8.91857 2.88209 8.6008 3.11645 8.36645C3.3508 8.13209 3.66857 8.0003 4 8H15.25C15.5815 8 15.8995 7.8683 16.1339 7.63388C16.3683 7.39946 16.5 7.08152 16.5 6.75C16.5 6.41848 16.3683 6.10054 16.1339 5.86612C15.8995 5.6317 15.5815 5.5 15.25 5.5H4C3.00577 5.50109 2.05258 5.89653 1.34956 6.59956C0.646531 7.30258 0.251092 8.25577 0.25 9.25V26.75C0.251092 27.7442 0.646531 28.6974 1.34956 29.4004C2.05258 30.1035 3.00577 30.4989 4 30.5Z" fill="#2F4CDD" />
											<path d="M25.25 0.5C24.0139 0.5 22.8055 0.866556 21.7777 1.55331C20.7499 2.24007 19.9488 3.21619 19.4758 4.35823C19.0027 5.50027 18.8789 6.75693 19.1201 7.96931C19.3613 9.1817 19.9565 10.2953 20.8306 11.1694C21.7047 12.0435 22.8183 12.6388 24.0307 12.8799C25.2431 13.1211 26.4997 12.9973 27.6418 12.5242C28.7838 12.0512 29.7599 11.2501 30.4467 10.2223C31.1334 9.19451 31.5 7.98613 31.5 6.75C31.498 5.093 30.8389 3.50442 29.6673 2.33274C28.4956 1.16106 26.907 0.501952 25.25 0.5ZM25.25 10.5C24.5083 10.5 23.7833 10.2801 23.1666 9.86801C22.5499 9.45596 22.0693 8.87029 21.7855 8.18506C21.5016 7.49984 21.4274 6.74584 21.5721 6.01841C21.7167 5.29098 22.0739 4.6228 22.5983 4.09835C23.1228 3.5739 23.791 3.21675 24.5184 3.07206C25.2458 2.92736 25.9998 3.00162 26.6851 3.28545C27.3703 3.56928 27.9559 4.04993 28.368 4.66661C28.7801 5.2833 29 6.00832 29 6.75C28.9989 7.74423 28.6035 8.69742 27.9004 9.40044C27.1974 10.1035 26.2442 10.4989 25.25 10.5Z" fill="#2F4CDD" />
											<path d="M6.5 13H12.75C13.0815 13 13.3995 12.8683 13.6339 12.6339C13.8683 12.3995 14 12.0815 14 11.75C14 11.4185 13.8683 11.1005 13.6339 10.8661C13.3995 10.6317 13.0815 10.5 12.75 10.5H6.5C6.16848 10.5 5.85054 10.6317 5.61612 10.8661C5.3817 11.1005 5.25 11.4185 5.25 11.75C5.25 12.0815 5.3817 12.3995 5.61612 12.6339C5.85054 12.8683 6.16848 13 6.5 13Z" fill="#2F4CDD" />
											<path d="M5.25 16.75C5.25 17.0815 5.3817 17.3995 5.61612 17.6339C5.85054 17.8683 6.16848 18 6.5 18H17.75C18.0815 18 18.3995 17.8683 18.6339 17.6339C18.8683 17.3995 19 17.0815 19 16.75C19 16.4185 18.8683 16.1005 18.6339 15.8661C18.3995 15.6317 18.0815 15.5 17.75 15.5H6.5C6.16848 15.5 5.85054 15.6317 5.61612 15.8661C5.3817 16.1005 5.25 16.4185 5.25 16.75Z" fill="#2F4CDD" />
										</svg>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${dashboard.withdraw }</span>
										</h3>
										<p class="mb-0">탈퇴회원</p>
										<small>(최근 일주일 기준)</small>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary">
										<svg width="32" height="36" viewBox="0 0 32 36" fill="none" xmlns="http://www.w3.org/2000/svg">
											<path d="M11.25 19.25C12.2389 19.25 13.2056 18.9568 14.0279 18.4074C14.8501 17.8579 15.491 17.0771 15.8694 16.1634C16.2478 15.2498 16.3469 14.2445 16.1539 13.2746C15.961 12.3046 15.4848 11.4137 14.7855 10.7145C14.0863 10.0152 13.1954 9.539 12.2255 9.34608C11.2555 9.15315 10.2502 9.25217 9.33658 9.6306C8.42295 10.009 7.64206 10.6499 7.09265 11.4722C6.54325 12.2944 6.25 13.2611 6.25 14.25C6.25129 15.5757 6.77849 16.8467 7.71589 17.7841C8.65329 18.7215 9.92431 19.2487 11.25 19.25ZM11.25 11.75C11.7445 11.75 12.2278 11.8966 12.6389 12.1713C13.05 12.446 13.3705 12.8365 13.5597 13.2933C13.7489 13.7501 13.7984 14.2528 13.702 14.7377C13.6055 15.2227 13.3674 15.6681 13.0178 16.0178C12.6681 16.3674 12.2227 16.6055 11.7377 16.702C11.2528 16.7984 10.7501 16.7489 10.2933 16.5597C9.83648 16.3705 9.44603 16.0501 9.17133 15.6389C8.89662 15.2278 8.75 14.7445 8.75 14.25C8.75089 13.5872 9.01457 12.9519 9.48322 12.4832C9.95187 12.0146 10.5872 11.7509 11.25 11.75Z" fill="#2F4CDD" />
											<path d="M30.78 22.4625C31.1927 21.9098 31.4684 21.2672 31.5844 20.5873C31.7005 19.9074 31.6537 19.2096 31.4478 18.5514L30.6543 15.9696C30.2817 14.7451 29.5244 13.6733 28.4946 12.9132C27.4648 12.1531 26.2174 11.7452 24.9375 11.75H19.3287C18.9971 11.75 18.6792 11.8817 18.4448 12.1161C18.2103 12.3505 18.0787 12.6685 18.0787 13C18.0787 13.3315 18.2103 13.6495 18.4448 13.8839C18.6792 14.1183 18.9971 14.25 19.3287 14.25H24.9375C25.6823 14.2474 26.4081 14.485 27.0073 14.9274C27.6064 15.3698 28.0471 15.9935 28.2639 16.706L29.0574 19.2866C29.145 19.5713 29.1645 19.8725 29.1145 20.1661C29.0645 20.4597 28.9463 20.7374 28.7694 20.977C28.5925 21.2166 28.3619 21.4114 28.0961 21.5456C27.8302 21.6799 27.5366 21.7499 27.2388 21.75H15.7777C15.7423 21.75 15.7127 21.7671 15.6777 21.7701C15.5937 21.7669 15.5125 21.75 15.4273 21.75H7.58978C6.20071 21.7449 4.84705 22.1879 3.72972 23.0132C2.61239 23.8385 1.79097 25.0021 1.3874 26.3312L0.454153 29.3625C0.236164 30.0719 0.187639 30.8225 0.31248 31.554C0.43732 32.2856 0.732043 32.9776 1.17296 33.5745C1.61388 34.1715 2.18869 34.6566 2.85119 34.9911C3.51369 35.3255 4.24541 35.4998 4.98753 35.5H18.0287C18.7708 35.4998 19.5026 35.3256 20.1652 34.9912C20.8277 34.6568 21.4026 34.1717 21.8436 33.5747C22.2845 32.9778 22.5793 32.2857 22.7042 31.5541C22.829 30.8226 22.7805 30.0719 22.5625 29.3625L21.6299 26.3315C21.3936 25.5767 21.0217 24.8713 20.5323 24.25H27.2388C27.9283 24.2532 28.6088 24.0928 29.2244 23.7821C29.8399 23.4714 30.3731 23.0191 30.78 22.4625ZM19.8328 32.089C19.6255 32.3726 19.3539 32.6031 19.0403 32.7614C18.7267 32.9198 18.38 33.0015 18.0287 33H4.98753C4.63653 32.9999 4.29043 32.9175 3.97708 32.7594C3.66373 32.6012 3.39187 32.3717 3.18337 32.0894C2.97487 31.807 2.83555 31.4796 2.77661 31.1336C2.71767 30.7876 2.74077 30.4326 2.84403 30.0971L3.77665 27.0661C4.02442 26.2489 4.52925 25.5335 5.21612 25.0261C5.90299 24.5188 6.73523 24.2466 7.58915 24.25H15.4267C16.2806 24.2466 17.1128 24.5188 17.7997 25.0261C18.4865 25.5335 18.9914 26.2489 19.2392 27.0661L20.1718 30.0971C20.2769 30.4323 20.301 30.7877 20.2421 31.134C20.1832 31.4804 20.0429 31.8078 19.8328 32.0894V32.089Z" fill="#2F4CDD" />
											<path d="M21.875 9.24999C22.7403 9.24999 23.5861 8.9934 24.3056 8.51267C25.0251 8.03194 25.5858 7.34866 25.917 6.54923C26.2481 5.74981 26.3347 4.87014 26.1659 4.02148C25.9971 3.17281 25.5804 2.39326 24.9686 1.78141C24.3567 1.16955 23.5772 0.752876 22.7285 0.584066C21.8798 0.415256 21.0002 0.501896 20.2008 0.833029C19.4013 1.16416 18.7181 1.72492 18.2373 2.44438C17.7566 3.16384 17.5 4.0097 17.5 4.875C17.5014 6.03489 17.9628 7.14688 18.7829 7.96705C19.6031 8.78722 20.7151 9.2486 21.875 9.24999ZM21.875 3C22.2458 3 22.6083 3.10997 22.9167 3.31599C23.225 3.52202 23.4654 3.81485 23.6073 4.15747C23.7492 4.50008 23.7863 4.87708 23.714 5.24079C23.6416 5.6045 23.463 5.9386 23.2008 6.20082C22.9386 6.46304 22.6045 6.64162 22.2408 6.71397C21.8771 6.78631 21.5001 6.74918 21.1575 6.60727C20.8149 6.46535 20.522 6.22503 20.316 5.91669C20.11 5.60835 20 5.24584 20 4.875C20.0006 4.37789 20.1983 3.9013 20.5498 3.54979C20.9013 3.19829 21.3779 3.00056 21.875 3Z" fill="#2F4CDD" />
										</svg>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${dashboard.total }</span>
										</h3>
										<p class="mb-0">Total</p>
										<small>(휴먼 계정 포함)</small>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-6 col-lg-12 col-sm-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="card-title">가입부터 첫 주문 까지 걸린 시간</h4>
                                    </div>
                                    <div class="card-body">
                                    <canvas id="chart"></canvas>
                                    </div>
                                </div>
                            </div>
					<div class="col-xl-6 col-lg-12 col-sm-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">최근 1개월 주문 회원 순위</h4>
                                <div class="memberBtn">
                                	<button class="btn light btn-primary listCoupon" data-bs-toggle="modal" data-bs-target="#couponModal" onclick="return giveCoupon()">쿠폰 지급</button>
                                </div>
                            </div>
                            <div class="card-body">
	                            <div class="table-responsive">
									<table class="table table-sm mb-0">
										<thead>
											<tr>
												<th>
													<div class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input" id="checkAll" />
														<label class="custom-control-label" for="checkAll"></label>
													</div>
												</th>
												<th>순위</th>
												<th>이름</th>
												<th>등급</th>
												<th>총 주문 건수</th>
												<th>총 주문 금액</th>
											</tr>
										</thead>
										<tbody class="orderList">
											<c:forEach var="member" items="${orderListMember }" varStatus="status">
											<fmt:formatNumber var="sum_order_count" value="${member.sum_order_count}" pattern="###,###,###" />
											<fmt:formatNumber var="sum_purchase_money" value="${member.sum_purchase_money}" pattern="###,###,###" />
												<tr id=${member.member_id }>
													<td>
														<div class="custom-control custom-checkbox ml-2">
															<input type="checkbox" class="custom-control-input ${member.member_grade_name }" id="coupon${member.member_no }" value="${member.member_no }" name="member" />
															<label class="custom-control-label" for="coupon${member.member_no }"></label>
														</div>
													</td>
													<c:choose>
														<c:when test="${status.count == 1}">
															<td id="first">${status.count}</td>
														</c:when>
														<c:when test="${status.count == 2 }">
															<td id="second">${status.count}</td>
														</c:when>
														<c:when test="${status.count == 3 }">
															<td id="third">${status.count}</td>
														</c:when>
														<c:otherwise>
															<td>${status.count}</td>
														</c:otherwise>
													</c:choose>

													<td>${member.member_id }</td>
													<td>${member.member_grade_name }</td>
													<td>${sum_order_count }</td>
													<td>${sum_purchase_money }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
                            </div>
                        </div>
                    </div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">최근 가입 회원</h4>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-hover table-responsive-sm">
										<thead>
											<tr>
												<th>회원번호</th>
												<th>이름</th>
												<th>아이디</th>
												<th>전화번호</th>
												<th>가입일</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="member" items="${recentRegistMember }">
												<fmt:formatDate var="member_regist_date" value="${member.member_regist_date}" pattern="yyyy-MM-dd" />
												<tr id="member${member.member_no }"  data-toggle="modal" data-target="#memberDetail" onclick="findMemberDetail('${member.member_no}')">
													<th>${member.member_no }</th>
													<td>${member.member_name }</td>
													<td>${member.member_id }</td>
													<td>${member.phone_number }</td>
													<td>${member_regist_date }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!-- 회원 상세정보 Modal -->

	<div class="modal" id="memberDetail">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">회원 상세 정보</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#memberDetail')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div>
						<table class="table table-bordered">
							<tr>
								<td>회원번호</td>
								<td id="member_no"></td>
								<td>등급</td>
								<td id="member_grade_name"></td>
							</tr>
							<tr>
								<td>아이디</td>
								<td id="member_id"></td>
								<td>가입일</td>
								<td id="member_regist_date"></td>
							</tr>
							<tr>
								<td>이름</td>
								<td id="member_name"></td>
								<td>생년월일</td>
								<td id="birth_date"></td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td id="phone_number"></td>
								<td>적립금</td>
								<td id="accrual_amount"></td>
							</tr>
							<tr>
								<td>이메일</td>
								<td id="email"></td>
								<td>총 실결제 금액</td>
								<td id="sum_purchase_money"></td>
							</tr>
							<tr>
								<td>주소</td>
								<td colspan="3" id="address"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#memberDetail')">닫기</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<!--쿠폰 지급 Modal -->

	<div class="modal" id="couponModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title">쿠폰 지급</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#couponModal')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div>
						쿠폰 이름 :<span class="col-sm-9 selectCoupon"></span>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#couponModal')">취소</button>
					<button type="button" class="btn btn-primary" onclick="return giveCouponToMember()">지급하기</button>
				</div>
			</div>
		</div>
	</div>
    
    
    <!--문자 발송 여부 Modal -->

	<div class="modal" id="messagingModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">문자보내기</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#messagingModal')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div>
						쿠폰 지급이 완료되었습니다.
						해당 회원에게 쿠폰 지급 알림 문자를 발송하시겠습니까?
						<input type="hidden" class="sendMember">
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#messagingModal')">아니오</button>
					<button type="button" class="btn btn-primary" onclick="return sendMessageToMember()">문자 발송하기</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 문자 발송 Modal -->
	<div class="modal" id="messagingToMemberModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">문자보내기</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#messagingToMemberModal')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">회원 번호</label>
						<div class="col-sm-9">
							<input class="form-control sendMember" type="text" name="to" disabled/>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">문자 내용</label>
						<div class="col-sm-9">
							<input class="form-control" id="sendContent" type="text"  name="content" placeholder="문자 내용 작성하기" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#messagingToMemberModal')">아니오</button>
					<button type="button" class="btn btn-primary" onclick="return sendMessage()">문자 발송하기</button>
				</div>
			</div>
		</div>
	</div>
	
	
<!-- Datatable -->
<script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>

<!-- Toastr -->
<script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>



<script>
	$(function(){
		$('#checkAll').change(function(){
				$( 'input[name="member"]').prop('checked', $('#checkAll').prop('checked'));
			
		});
		
		makechart();
	   
	})
	
	function makechart(){
		
		 $.ajax({
				url : "./takeTimeToOrder",
				type : "post",
				dataType : "text",
				async : false,
				success : function(data) {
					let chartInfo = JSON.parse(data);
					let result = [chartInfo.no, chartInfo.day, chartInfo.week, chartInfo.month, chartInfo.hundred, chartInfo.elses];
					drawchart(result)
				}
			});
	}
	
	function drawchart(result){
		 const bar_chart = document.getElementById("chart").getContext('2d');
		    new Chart(bar_chart, {
		        type: 'bar',
		        data: {
					weight: 5,	
		            defaultFontFamily: 'Poppins',
		            datasets: [{
		            	label : '첫 주문까지 걸린 시간',
		                data: result,
		                borderWidth: 1, 
						borderColor: "rgba(255,255,255,1)",
		                backgroundColor: [
		                	'rgba(255, 99, 132, 0.2)',
		                    'rgba(54, 162, 235, 0.2)',
		                    'rgba(255, 206, 86, 0.2)',
		                    'rgba(75, 192, 192, 0.2)',
		                    'rgba(153, 102, 255, 0.2)',
		                    'rgba(255, 159, 64, 0.2)'
		                ],
		                hoverBackgroundColor: [
		                	  'rgba(255, 99, 132, 1)',
		                      'rgba(54, 162, 235, 1)',
		                      'rgba(255, 206, 86, 1)',
		                      'rgba(75, 192, 192, 1)',
		                      'rgba(153, 102, 255, 1)',
		                      'rgba(255, 159, 64, 1)'
		                ]
	
		            }],
		             labels: [
		                 "주문 안 함",
		                 "하루",
		                 "일주일 이내",
		                 "30일 이내",
		                 "100일 이내",
		                 "그 외"
		                 
		            ]
		        },
		        options:{
		        	scales:{
		        		yAxes:[{
		        			display : true,
		        			ticks: {
		        				beginAtZero : true
		        			}
		        		}],
		        	
		        	}
		        	
		        }
		})
	}
	
	function findMemberDetail(member){
		$.ajax({
			url : "./searchMember",
			type : "get",
			data :{
				"member_no" : member
			},
			dataType : "text",
			async : false,
			success : function(data) {
				let memberInfo = JSON.parse(data);
				$('#member_no').html(memberInfo[0].member_no);
				$('#member_grade_name').html(memberInfo[0].member_grade_name);
				$('#member_id').html(memberInfo[0].member_id);
				$('#member_name').html(memberInfo[0].member_name);
				$('#phone_number').html(memberInfo[0].phone_number);
				$('#email').html(memberInfo[0].email);
				$('#birth_date').html(memberInfo[0].birth_date + "( " + memberInfo[0].gender + " )" );
				$('#address').html(memberInfo[0].address);
				$('#member_regist_date').html(new Date(memberInfo[0].member_regist_date).toLocaleString());
				$('#accrual_amount').html(memberInfo[0].accrual_amount.toLocaleString());
				$('#sum_purchase_money').html(memberInfo[0].sum_purchase_money.toLocaleString());
	
			}
		});
	
		
	}
	
	function modalReset(modalName) {
		$( 'input[type="checkbox"]').prop('checked', false);		
		$(modalName).hide();
	
	}
	
		let result = [];
	function giveCoupon(){
		result= [];
		let coupon = {
				"member_grade_name" : "all"
		};
	  	let classresult = [];
		
		  // 선택된 목록 가져오기
		  const query = 'input[name="member"]:checked';
		  const selectedEls = 
		      document.querySelectorAll(query);
		
		  selectedEls.forEach((el) => {
			  if(!classresult.includes(el.className.split(' ')[1])){
				  let member_grade_name = el.className.split(' ')[1];
				  classresult.push(member_grade_name);
			  }
			    result.push(el.value)
			  });
		  
		  if(result.length == 0){
				toastError("쿠폰 지급 대상을 선택하세요.", "쿠폰 지급 대상");
				return false;
			} else{
				$('#couponModal').show();
			}
		  if(classresult.length == 1){
			  coupon = {
					  "member_grade_name" : classresult[0]
			  }
		  }
	
		  $.ajax({
			url : "./coupon",
			type : "post",
			contentType : 'application/json',
			data : JSON.stringify(coupon),
			dataType : "text",
			async : false,
			success : function(data) {
				let output ="<select id='choose_coupon' name='member_grade_name'>";
				output += "<option value='0'>쿠폰을 선택하세요.</option>";
				$.each(JSON.parse(data),function(index, coupon) {
					output += `<option value="\${coupon.coupon_policy_no}" class="\${coupon.member_grade_name}">\${coupon.coupon_policy_name}</option>`;
				});
	
				output+= "</select>";
	
				$('.selectCoupon').html(output);
				$('#choose_coupon').selectpicker('refresh');
	
			}
		});
	}
	
	function giveCouponToMember() {
		let memberDTO = {
				"coupon_policy_no" : $("#choose_coupon").val(),
				"member_no" : result
		}
		if ($("#choose_coupon").val() == '0') {
			toastError('쿠폰을 선택해주세요.', '쿠폰 선택');
			return false;
		}
		
		$.ajax({
			url : "./giveCouponToMember",
			type : "post",
			contentType : 'application/json',
			data : JSON.stringify(memberDTO),
			dataType : "text",
			async : false,
			success : function(data) {
				$('#messagingModal').show();
				$('.sendMember').val(result);
				$('#couponModal').hide();
				$( 'input[type="checkbox"]').prop('checked', false);
	
			}
		});
		
	}
	
	function sendMessageToMember(){
		$('#messagingToMemberModal').show();
		$('#messagingModal').hide();
	}
	
	function sendMessage(){
		let message = {
				"to" : $('.sendMember').val(),
				"content" : $('#sendContent').val()
		}
		$.ajax({
			url : "./sendMessageToMember",
			type : "post",
			contentType : 'application/json',
			data : JSON.stringify(message),
			dataType : "text",
			async : false,
			success : function(data) {
				toastSuccess('문자 발송에 성공했습니다!', '문자 발송');
				$('#messagingToMemberModal').hide();
	
			}
		});
	}
	
	 function toastSuccess(message, title) {
	     toastr.success(message, title, {
	         positionClass: "toast-top-center",
	         timeOut: 5e3,
	         closeButton: !0,
	         debug: !1,
	         newestOnTop: !0,
	         progressBar: !0,
	         preventDuplicates: !0,
	         onclick: null,
	         showDuration: "300",
	         hideDuration: "1000",
	         extendedTimeOut: "1000",
	         showEasing: "swing",
	         hideEasing: "linear",
	         showMethod: "fadeIn",
	         hideMethod: "fadeOut",
	         tapToDismiss: !1
	     })
		}
		
		function toastError(message, title) {

	     toastr.error(message, title, {
	         positionClass: "toast-top-center",
	         timeOut: 5e3,
	         closeButton: !0,
	         debug: !1,
	         newestOnTop: !0,
	         progressBar: !0,
	         preventDuplicates: !0,
	         onclick: null,
	         showDuration: "300",
	         hideDuration: "1000",
	         extendedTimeOut: "1000",
	         showEasing: "swing",
	         hideEasing: "linear",
	         showMethod: "fadeIn",
	         hideMethod: "fadeOut",
	         tapToDismiss: !1
	     })
	 }
</script>
</body>
</html>
