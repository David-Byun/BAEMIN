<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 <!-- 다음주소 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!--
	시큐리티 태그 라이브러리를 사용하여 principal 이름으로 회원정보를 참조할 수 있도록 설정
	header.jsp는 모든 페이지의 레이아웃으로 사용되므로 어디에서든 현재 로그인된 사용자의 정보를 사용할 수 있습니다.
-->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>


<style>
header .admin_page_btn {
	font-size: 13px;
	position: absolute;
	right: 10px;
	top: 10px;
	font-weight: bold;
}

header .admin_page_btn a {
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 5px;
	background: #fff;
	font-size: 13px;
	display: block;
}
</style>

</head>
<body >
	<!-- <body> -->


	<header>
		<div id="header">
			<a href="/"><img src="/img/baemin.jpg" alt="이미지"></a>

			<!-- 임시 -->
			<c:if test="${principal.role eq 'ROLE_ADMIN' }"> 
				<div class="admin_page_btn">
					<div>
						<a href="/admin/main">사장님 페이지</a>
					</div>
				</div>
			</c:if>
			<!-- 임시 -->
			
			<div class="menu_tab_box active">
				<div class="menu_tab">
					<span> </span>
					<span> </span>
					<span> </span>
				</div>
			</div>

		</div>
	</header>
	<!-- 헤더 -->
 

