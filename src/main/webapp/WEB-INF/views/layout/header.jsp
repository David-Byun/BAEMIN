<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 <!-- 다음주소 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

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
 

